import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IActivityResult } from 'app/shared/model/activity-result.model';

type EntityResponseType = HttpResponse<IActivityResult>;
type EntityArrayResponseType = HttpResponse<IActivityResult[]>;

@Injectable({ providedIn: 'root' })
export class ActivityResultService {
  public resourceUrl = SERVER_API_URL + 'api/activity-results';

  constructor(protected http: HttpClient) {}

  create(activityResult: IActivityResult): Observable<EntityResponseType> {
    return this.http.post<IActivityResult>(this.resourceUrl, activityResult, { observe: 'response' });
  }

  update(activityResult: IActivityResult): Observable<EntityResponseType> {
    return this.http.put<IActivityResult>(this.resourceUrl, activityResult, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IActivityResult>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IActivityResult[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
