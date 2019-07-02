import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApiService } from '../service/api/api.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.less']
})
export class DashboardComponent implements OnInit {
  homeStats: any;
  mean: number;
  constructor(private router: Router, private apiService: ApiService) {
    this.homeStats = this.router.getCurrentNavigation().extras.state;
  }
  ngOnInit() {
    if (this.homeStats === undefined) {
      this.apiService.callApi({ method: 'get', url: environment.apiUrl }).subscribe(data => {
        this.mean = Math.round(data.body.computersCount / data.body.companyCount);
        this.homeStats = {
          computersCount: data.body.computersCount,
          companiesCount: data.body.companyCount,
          computerCountByCompany: Object.keys(data.body.computerCountByCompany)
            .map(key => ({
              name: key,
              value: data.body.computerCountByCompany[key],
              colSpan: 3, rowSpan: 1
            })
            )
        };
      });
    }
  }
}
