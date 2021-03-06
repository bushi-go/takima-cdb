import { Component } from '@angular/core';

import { ApiService } from './components/service/api/api.service';
import { Router } from '@angular/router';
import { TableComponent } from './components/table/table.component';
import { ApiCall } from './model/api';
import { environment } from 'src/environments/environment';


import _ from 'lodash';
import mapLinkAndAffordancesToApiCalls from './util/linkUtil';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less'],
})
export class AppComponent {
  title = 'Computer Database System V0.1';
  navLinks: any[];
  activeLinkIndex = -1;
  stats: any;
  homeStats: any;
  constructor(private apiService: ApiService, private router: Router) {
    this.navLinks = [];
    const baseApiCall: ApiCall = { rel: '', method: 'get', url: environment.apiUrl };
    this.apiService.callApi(baseApiCall).subscribe((data: any) => {

      this.homeStats = { computersCount: data.body.computersCount,
            companiesCount: data.body.companyCount,
            computerCountByCompany: Object.keys(data.body.computerCountByCompany)
              .map(key => ({
                name: key, value: data.body.computerCountByCompany[key]
              })
              )
          };
      const apiCalls = mapLinkAndAffordancesToApiCalls(data.body._links);
      apiCalls.forEach(call => {
        this.router.config.unshift({
          path: call.rel,
          component: TableComponent,
          data: { apiCalls: call }
        });

        if (call.rel !== 'self') {
          this.navLinks.unshift({ path: call.rel, label: call.rel.toUpperCase() });
        }


      });
      this.router.navigate(['dashboard'], {state: this.homeStats});
      this.navLinks.unshift({path: 'dashboard', label: 'dashboard'.toUpperCase()});
    });
  }
}
