import { InputType, FieldType } from './field';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
  })
export class DomainConfig<T> {
    constructor(rel: string, empty: () => T, properties: PropertyConfig[]) {
        this.model = empty();
        this.properties = properties;
        this.rel = rel;
        this.refList = {};
    }
    refList?: {[key: string]: any[]};
    properties: PropertyConfig[];
    rel: string;
    model: T;
    validate: () => boolean;
}

export interface PropertyConfig {
    name: string;
    isId?: boolean;
    options?: {
        label: string;
        required: boolean;
        fieldType: FieldType;
        inputType?: InputType;
         referenceListUri?: string;
         refRel?:string;
    }|undefined;
}


