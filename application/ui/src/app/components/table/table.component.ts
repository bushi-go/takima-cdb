import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { merge, of as observableOf } from 'rxjs';
import { ApiService } from '../service/api/api.service';
import { isWebCollection, WebResource } from 'src/app/model/web-resource';
import { ApiCall } from 'src/app/model/api';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { catchError, map, startWith, switchMap, tap, expand, first } from 'rxjs/operators';
import mapLinkAndAffordancesToApiCalls from 'src/app/util/linkUtil';
import { mapWebCollectionToTabularData, toDataObjectList } from '../../util/resourceUtil';
import { Row } from 'src/app/model/tabular-data';
import { Router } from '@angular/router';
import { getDomainConfigFor } from 'src/app/domain';
import { MatDialog } from '@angular/material/dialog';
import { DialogComponent } from '../dialog/dialog.component';
import { DomainConfig } from 'src/app/model/domain-config';
import { MatTableDataSource } from '@angular/material/table';
import { trigger, state, transition, style, animate } from '@angular/animations';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.less'],
  providers: [ApiService],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})

export class TableComponent implements AfterViewInit {

  constructor(public dialog: MatDialog, private route: ActivatedRoute, private apiService: ApiService, private snackBar: MatSnackBar) {
  }
  domainConfig: DomainConfig<any>;
  hasForm = false;
  baseApiCall: ApiCall;
  resourceApiCall: ApiCall[] = [];
  displayedColumn: string[];
  displayedColumnWithActions: string[] = ['actions-col'];
  dataSource: MatTableDataSource<any>;
  relation: string;
  isLoadingResults = true;
  resultsLength = 0;
  createForm: any;
  stickyHeader = false;
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  @ViewChild(MatSort, { static: false }) sort: MatSort;

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    this.route.data.subscribe((data) => { this.baseApiCall = data.apiCalls; });
    merge(this.route.data, this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.stickyHeader = false;
          this.isLoadingResults = true;
          this.baseApiCall.queryParams = {
            pageIndex: this.paginator.pageIndex,
            pageSize: this.paginator.pageSize,
            direction: this.sort.direction.toUpperCase(),
            sortBy: 'name'
          };
          return this.apiService.callApi(this.baseApiCall);
        }),
        map((data) => {
          if (isWebCollection(data.body)) {
            this.relation = Object.keys(data.body._embedded)[0];
            this.isLoadingResults = false;
            this.resourceApiCall = mapLinkAndAffordancesToApiCalls(data.body._links, data.body._templates);
            const sample = data.body._embedded[this.relation][0];
            if (this.resourceApiCall.findIndex((apiCall) => apiCall.method === 'post') !== -1 && sample !== undefined && sample !== null) {
              this.domainConfig = getDomainConfigFor(sample);
              this.domainConfig.properties.forEach(prop => {
                if (prop.options && prop.options.referenceListUri && prop.options.refRel) {
                  this.apiService.callApi({ url: prop.options.referenceListUri, method: 'get' })
                    .subscribe(res => {
                      if (isWebCollection(res.body)) {
                        this.domainConfig.refList[prop.options.refRel] = toDataObjectList(res.body);
                      }
                    });
                }
              });
            }
            return data;
          }
        }),
        catchError((err) => {
          console.log(err);
          this.isLoadingResults = false;
          return observableOf(err);
        })
      ).subscribe(data => {
        if (!data.error) {
        this.resultsLength = Number.parseInt(data.headers.get('X-TOTAL-COUNT'), 10);
        
        const dataTab = mapWebCollectionToTabularData(data.body);
        this.dataSource = new MatTableDataSource(dataTab.rows);
        this.displayedColumn = dataTab.header;
        if (this.displayedColumnWithActions.length === 1) {
          this.displayedColumnWithActions.push(...dataTab.header);
        }
        this.dataSource.filterPredicate = this.filterData;
        this.hasForm = true;
        this.stickyHeader = true;
      }else{
        this.snackBar.open(data.error.message, '', {duration: 2000});
      }
    });
  }
  filterData(data: Row, filterValue: string) {
    return Object.values(data.data).join(',').toLowerCase().indexOf(filterValue) !== -1;
  }
  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  onAction(action: any) {
    if (action !== 'cancel') {
    this.paginator._changePageSize(this.paginator.pageSize);
    }
  }
  openDialog(data: {data: any, call: ApiCall, mode: string}): void {
    const dialogLoad = {form: this.domainConfig, call: this.resourceApiCall.find((apiCall) => apiCall.method === 'post'), mode: 'create'};
    if (data) {
      this.domainConfig.model = data.data;
      dialogLoad.form.model = data.data;
      dialogLoad.call = data.call;
      dialogLoad.mode = data.mode;
    }
    const dialogRef = this.dialog.open(DialogComponent, {
      width: '350px',
      data: dialogLoad
    });
    dialogRef.afterClosed().subscribe((result: string) => {
      if (result !== 'cancel' && result.indexOf('Error') === -1) {
        this.paginator._changePageSize(this.paginator.pageSize);
        this.snackBar.open(result, '' , {
          duration: 2000
        });
      } else if (result.indexOf('Error') !== -1) {
        this.snackBar.open(result, '' , {
          duration: 2000
        });
      }
    });
  }
}
