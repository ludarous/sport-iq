import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserActivityResult } from 'app/shared/model/user-activity-result.model';

type EntityResponseType = HttpResponse<IUserActivityResult>;
type EntityArrayResponseType = HttpResponse<IUserActivityResult[]>;

@Injectable({ providedIn: 'root' })
export class UserActivityResultService {
  public resourceUrl = SERVER_API_URL + 'api/user-activity-results';

  constructor(protected http: HttpClient) {}

  create(userActivityResult: IUserActivityResult): Observable<EntityResponseType> {
    return this.http.post<IUserActivityResult>(this.resourceUrl, userActivityResult, { observe: 'response' });
  }

  update(userActivityResult: IUserActivityResult): Observable<EntityResponseType> {
    return this.http.put<IUserActivityResult>(this.resourceUrl, userActivityResult, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserActivityResult>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserActivityResult[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
