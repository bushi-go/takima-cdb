import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Row } from 'src/app/model/tabular-data';
import { ApiCall } from 'src/app/model/api';
import { ApiService } from '../service/api/api.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-actions',
  templateUrl: './actions.component.html',
  styleUrls: ['./actions.component.less']
})
export class ActionsComponent implements OnInit {
  @Input() input: Row;
  @Output() action: EventEmitter<any> = new EventEmitter();
  @Output() dialog: EventEmitter<any> = new EventEmitter();
  data: any;
  operations: {call: ApiCall, icon: string, label?: string}[];
  navigateLinks: {call: ApiCall, icon: string}[];
  constructor(private apiService: ApiService) {

  }

  ngOnInit() {
    this.data = this.input.data;
    this.operations = this.input.operations.filter(op => op.call.method !== 'get');
  }
  onAction(operation: ApiCall) {
    switch (operation.method) {
      case 'put':
        this.openDialog({data: this.data, call: operation, mode: 'replace'});
        break;
      case 'patch':
        this.openDialog('update');
        break;
      case 'delete':

        this.apiService.callApi(operation).subscribe((data => {
          this.action.emit('deleted');
        }));
        break;
    }
  }
  openDialog(payload: any) {
    this.dialog.emit(payload);
  }

}
