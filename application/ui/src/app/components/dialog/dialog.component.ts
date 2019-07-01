import { Component, OnInit, Inject, EventEmitter, Output } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DomainConfig } from 'src/app/model/domain-config';
import { ApiCall } from 'src/app/model/api';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.less']
})
export class DialogComponent {
  constructor(
    public dialogRef: MatDialogRef<DialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {form: DomainConfig<any>, call?: ApiCall}) {
    }
    onNoClick(): void {
      this.dialogRef.close();
    }
    onSave(save:String){
      this.dialogRef.close(save);
    }
    
    onCancel(cancel:String){
      this.dialogRef.close(cancel);
    }
}
