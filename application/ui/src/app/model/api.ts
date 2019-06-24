import { HttpHeaders, HttpParams } from '@angular/common/http';

export interface ApiCall {
    rel: string;
    method: 'put'|'patch'|'post'|'get'|'delete';
    url: string;
    queryParams?: {[key: string]: any};
    options?: {
      body?: any;
        headers?: HttpHeaders | {
            [header: string]: string | string[];
        };
        reportProgress?: boolean;
        observe: 'response';
        params?: HttpParams | {
            [param: string]: string | string[];
        };
        responseType?: 'json';
        withCredentials?: boolean;
    };
  }