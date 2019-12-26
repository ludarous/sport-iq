import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IActivityResultSplit } from 'app/shared/model/activity-result-split.model';

type EntityResponseType = HttpResponse<IActivityResultSplit>;
type EntityArrayResponseType = HttpResponse<IActivityResultSplit[]>;

@Injectable({ providedIn: 'root' })
export class ActivityResultSplitService {
  public resourceUrl = SERVER_API_URL + 'api/activity-result-splits';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/activity-result-splits';

  constructor(protected http: HttpClient) {}

  create(activityResultSplit: IActivityResultSplit): Observable<EntityResponseType> {
    return this.http.post<IActivityResultSplit>(this.resourceUrl, activityResultSplit, { observe: 'response' });
  }

  update(activityResultSplit: IActivityResultSplit): Observable<EntityResponseType> {
    return this.http.put<IActivityResultSplit>(this.resourceUrl, activityResultSplit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IActivityResultSplit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IActivityResultSplit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IActivityResultSplit[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
