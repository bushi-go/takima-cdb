import { Injectable } from '@angular/core';
import { DomainConfig } from 'src/app/model/domain-config';
import { Field, FieldType, InputType } from 'src/app/model/field';
import { FormBuilder, Validators, FormControl } from '@angular/forms';
import { ApiService } from './api/api.service';
import { toDataObjectList } from 'src/app/util/resourceUtil';
import { isWebCollection } from 'src/app/model/web-resource';

@Injectable({
  providedIn: 'root'
})
export class FormService {

  constructor(private formBuilder: FormBuilder, private apiService: ApiService) {
  }

  toFields(formDomain: DomainConfig<any>): Field[] {
    const fieldList: Field[] = [];
    formDomain.properties.forEach(prop => {
      if (prop.options && prop.options.referenceListUri) {
        // TODO : cache reflists in a store, and get them with the first call to the resource collection
        // This is the wrong way.
            fieldList.unshift({
              key: prop.name,
              label: prop.name || '',
              controlType: prop.options && prop.options.fieldType || FieldType.hidden,
              type: prop.options && prop.options.inputType || InputType.text,
              value: formDomain.model[prop.name] || '',
              required: prop.options && prop.options.required || false,
              refList: formDomain.refList[prop.options.refRel]
          });
      } else {
        fieldList.unshift({
          key: prop.name,
          label: prop.name || '',
          controlType: prop.options && prop.options.fieldType || FieldType.hidden,
          type: prop.options && prop.options.inputType || InputType.text,
          value: formDomain.model[prop.name] || '',
          required: prop.options && prop.options.required || false
      });
    }});
    return fieldList;
  }
  toFormGroup(fields: Field[]){
    
    const form:any = {};
    fields.forEach(field =>{
      
      form[field.key] = field.required ? new FormControl(field.value || '', Validators.required) : new FormControl(field.value || '');
    }); 
      return this.formBuilder.group(form);
  }
}
