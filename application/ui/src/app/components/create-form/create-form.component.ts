import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ApiService } from '../service/api/api.service';
import { DomainConfig } from 'src/app/model/domain-config';
import { FormBuilder, FormGroup, Validators  } from '@angular/forms';
import { FormService } from '../service/form.service';
import { Field, FieldType } from 'src/app/model/field';
import { ApiCall } from 'src/app/model/api';

@Component({
  selector: 'app-create-form',
  templateUrl: './create-form.component.html',
  styleUrls: ['./create-form.component.less'],
  providers: [FormService]
})
export class CreateFormComponent implements OnInit {
  @Input() data: {form: DomainConfig<any>, call: ApiCall};
  @Output() saved: EventEmitter<any> = new EventEmitter();
  @Output() cancel: EventEmitter<any> = new EventEmitter();
  fields: Field[];
  form: FormGroup;
  save: ApiCall;
  public fieldTypes = FieldType;
  constructor(private formService: FormService, private apiService: ApiService) {

  }
  ngOnInit() {
    this.save = this.data.call;
    this.fields = this.formService.toFields(this.data.form);
    this.form = this.formService.toFormGroup(this.fields);
    }
  onSubmit() {
    const result = this.form.value;
    this.save.options = {body: result, headers: {
    'content-type': 'application/json'}};
    this.apiService.callApi(this.save).subscribe((callResult) => {
      this.saved.emit('saved ' + JSON.stringify(callResult.body));
    });
  }
  onCancel() {
    this.cancel.emit();
  }
}
