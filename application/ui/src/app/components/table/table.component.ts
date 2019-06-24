import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { merge, of as observableOf } from 'rxjs';
import { ApiService } from '../service/api.service';
import { isWebCollection, WebResource} from 'src/app/model/web-resource';
import { ApiCall } from 'src/app/model/api';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import {catchError, map, startWith, switchMap, tap, expand, first} from 'rxjs/operators';
import mapLinkAndAffordancesToApiCalls from 'src/app/util/linkUtil';
import {mapWebCollectionToTabularData} from '../../util/resourceUtil';
import { TabularData, Row } from 'src/app/model/tabularData';
import { HttpHeaders } from '@angular/common/http';
@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.less'],
  providers: [ApiService]
})



export class TableComponent implements AfterViewInit {

  constructor(private route: ActivatedRoute, private apiService: ApiService) {
    this.data = {header: [], rows: []};
  }
  baseApiCall: ApiCall;
  resourceApiCall: ApiCall[] = [];
  data: TabularData;
  tableHead;
  tablePipe;
  relation: string;
  isLoadingResults = true;
  resultsLength = 0;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    this.route.data.subscribe((data) => {this.baseApiCall = data.apiCalls; });
    merge(this.route.data, this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          this.baseApiCall.queryParams ={
              pageIndex: this.paginator.pageIndex,
              pageSize: this.paginator.pageSize,
              direction: this.sort.direction.toUpperCase(),
              sortBy: 'name'
          };
          return  this.apiService.callApi(this.baseApiCall);
        }),
        map((data) => {
          if (isWebCollection(data.body)) {  
            this.relation = Object.keys(data.body._embedded)[0];
            this.isLoadingResults = false;
            this.resourceApiCall = mapLinkAndAffordancesToApiCalls(data.body._links, data.body._templates);
            this.resultsLength = Number.parseInt(data.headers.get('X-TOTAL-COUNT'), 10);
            return mapWebCollectionToTabularData(data.body);
          }
        }),
        catchError((err) => {
          console.log(err);
          this.isLoadingResults = false;
          return observableOf({header:[] as string[], rows:[]as Row[]});
        })
      ).subscribe(data => {
        this.data = data;
        });
  }
}
