import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';

type EntityResponseType = HttpResponse<IAthleteActivityResultSplit>;
type EntityArrayResponseType = HttpResponse<IAthleteActivityResultSplit[]>;

@Injectable({ providedIn: 'root' })
export class AthleteActivityResultSplitService {
  public resourceUrl = SERVER_API_URL + 'api/athlete-activity-result-splits';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/athlete-activity-result-splits';

  constructor(protected http: HttpClient) {}

  create(athleteActivityResultSplit: IAthleteActivityResultSplit): Observable<EntityResponseType> {
    return this.http.post<IAthleteActivityResultSplit>(this.resourceUrl, athleteActivityResultSplit, { observe: 'response' });
  }

  update(athleteActivityResultSplit: IAthleteActivityResultSplit): Observable<EntityResponseType> {
    return this.http.put<IAthleteActivityResultSplit>(this.resourceUrl, athleteActivityResultSplit, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAthleteActivityResultSplit>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAthleteActivityResultSplit[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAthleteActivityResultSplit[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
