import { Component, OnInit, Inject, EventEmitter, Output } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DomainConfig } from 'src/app/model/domain-config';
import { ApiCall } from 'src/app/model/api';
import _ from 'lodash';
import pluralize from 'pluralize';
@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.less']
})
export class DialogComponent {
  title: string;
  constructor(
    public dialogRef: MatDialogRef<DialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {form: DomainConfig<any>, call?: ApiCall, mode; string}) {
      switch (data.mode) {
        case 'create':
          this.title = _.capitalize(data.mode) + ' New ' + _.capitalize(pluralize.singular(data.form.rel));
          break;
        case 'update':
          this.title = _.capitalize(data.mode) +
          ' ' + _.capitalize(pluralize.singular(data.form.rel))
          + ' #' + data.form.model[data.form.properties.find(prop => prop.isId).name];
          break;
        case 'replace':
            this.title = _.capitalize(data.mode) +
            ' ' + _.capitalize(pluralize.singular(data.form.rel))
            + ' #' + data.form.model[data.form.properties.find(prop => prop.isId).name];
            break;
      }
    }
    onNoClick(): void {
      this.dialogRef.close('cancel');
    }
    onSave(save: string) {
      this.dialogRef.close(save);
    }
    onCancel(cancel: string) {
      this.dialogRef.close(cancel);
    }
    onErrorOnAction(error: any) {
      this.dialogRef.close(error);
    }
}
