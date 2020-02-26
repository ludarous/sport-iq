import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAthleteActivity } from 'app/shared/model/athlete-activity.model';

type EntityResponseType = HttpResponse<IAthleteActivity>;
type EntityArrayResponseType = HttpResponse<IAthleteActivity[]>;

@Injectable({ providedIn: 'root' })
export class AthleteActivityService {
  public resourceUrl = SERVER_API_URL + 'api/athlete-activities';

  constructor(protected http: HttpClient) {}

  create(athleteActivity: IAthleteActivity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(athleteActivity);
    return this.http
      .post<IAthleteActivity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(athleteActivity: IAthleteActivity): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(athleteActivity);
    return this.http
      .put<IAthleteActivity>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAthleteActivity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAthleteActivity[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(athleteActivity: IAthleteActivity): IAthleteActivity {
    const copy: IAthleteActivity = Object.assign({}, athleteActivity, {
      date: athleteActivity.date && athleteActivity.date.isValid() ? athleteActivity.date.toJSON() : undefined
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
      res.body.forEach((athleteActivity: IAthleteActivity) => {
        athleteActivity.date = athleteActivity.date ? moment(athleteActivity.date) : undefined;
      });
    }
    return res;
  }
}
