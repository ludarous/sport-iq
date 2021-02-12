import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserActivity } from 'app/shared/model/user-activity.model';

type EntityResponseType = HttpResponse<IUserActivity>;
type EntityArrayResponseType = HttpResponse<IUserActivity[]>;

@Injectable({ providedIn: 'root' })
export class UserActivityService {
  public resourceUrl = SERVER_API_URL + 'api/user-activities';

  constructor(protected http: HttpClient) {}

  create(userActivity: IUserActivity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userActivity);
    return this.http
      .post<IUserActivity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userActivity: IUserActivity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userActivity);
    return this.http
      .put<IUserActivity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserActivity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserActivity[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userActivity: IUserActivity): IUserActivity {
    const copy: IUserActivity = Object.assign({}, userActivity, {
      date: userActivity.date && userActivity.date.isValid() ? userActivity.date.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((userActivity: IUserActivity) => {
        userActivity.date = userActivity.date ? moment(userActivity.date) : undefined;
      });
    }
    return res;
  }
}
