import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserActivityResultSplit } from 'app/shared/model/user-activity-result-split.model';

type EntityResponseType = HttpResponse<IUserActivityResultSplit>;
type EntityArrayResponseType = HttpResponse<IUserActivityResultSplit[]>;

@Injectable({ providedIn: 'root' })
export class UserActivityResultSplitService {
  public resourceUrl = SERVER_API_URL + 'api/user-activity-result-splits';

  constructor(protected http: HttpClient) {}

  create(userActivityResultSplit: IUserActivityResultSplit): Observable<EntityResponseType> {
    return this.http.post<IUserActivityResultSplit>(this.resourceUrl, userActivityResultSplit, { observe: 'response' });
  }

  update(userActivityResultSplit: IUserActivityResultSplit): Observable<EntityResponseType> {
    return this.http.put<IUserActivityResultSplit>(this.resourceUrl, userActivityResultSplit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IUserActivityResultSplit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IUserActivityResultSplit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
