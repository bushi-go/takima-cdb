import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpHeaders, HttpEvent, HttpResponse } from '@angular/common/http';
import { WebResource, WebCollection } from 'src/app/model/web-resource';
import { Observable } from 'rxjs';
import { ApiCall } from 'src/app/model/api';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  constructor(private httpClient: HttpClient) {
  }
  callApi(apiCallSpec: ApiCall) {
    if (apiCallSpec.queryParams !== undefined && apiCallSpec.formProperties === undefined) {
      const queryString = Object.keys(apiCallSpec.queryParams).map((param) => {
        return encodeURIComponent(param) + '=' + encodeURIComponent(apiCallSpec.queryParams[param]);
      }).reduce((acc, val) => acc + '&' + val);

      if (apiCallSpec.url.match(/\?.*/)!==null) {
        apiCallSpec.url = apiCallSpec.url.replace(/\?.*/, '?' + queryString);
      } else {
        apiCallSpec.url.concat('?' + queryString);
      }
    } else {
      if (apiCallSpec.url.match(/\?.*/)!==null && apiCallSpec.method!=='get') {
        apiCallSpec.url = apiCallSpec.url.replace(/\?.*/, '');
      }

    }
    return this.httpClient.request(apiCallSpec.method, apiCallSpec.url, { ...apiCallSpec.options, observe: 'response' });
  }
}
