import { Injectable } from '@angular/core';
import {HttpClient, HttpParams, HttpHeaders, HttpEvent, HttpResponse} from '@angular/common/http';
import { WebResource, WebCollection } from 'src/app/model/web-resource';
import { Observable } from 'rxjs';
import { ApiCall } from 'src/app/model/api';

@Injectable()
export class ApiService {
  constructor(private httpClient: HttpClient) {
  }
  callApi(apiCallSpec: ApiCall) {
    
    if (apiCallSpec.queryParams !== undefined) {
      const queryString = Object.keys(apiCallSpec.queryParams).map((param) => {
        return encodeURIComponent(param) + '=' + encodeURIComponent(apiCallSpec.queryParams[param]);
      }).reduce((acc, val) => acc + '&' + val);

      if (apiCallSpec.url.match(/\?.*/).length > 0) {
        apiCallSpec.url = apiCallSpec.url.replace(/\?.*/, '?' + queryString);
      } else {
        apiCallSpec.url.concat('?' + queryString);
      }
    }
    return this.httpClient.request(apiCallSpec.method, apiCallSpec.url, {...apiCallSpec.options, observe:'response'});
  }
}
