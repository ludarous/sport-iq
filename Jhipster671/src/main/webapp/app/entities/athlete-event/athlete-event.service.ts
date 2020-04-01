import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAthleteEvent } from 'app/shared/model/athlete-event.model';

type EntityResponseType = HttpResponse<IAthleteEvent>;
type EntityArrayResponseType = HttpResponse<IAthleteEvent[]>;

@Injectable({ providedIn: 'root' })
export class AthleteEventService {
  public resourceUrl = SERVER_API_URL + 'api/athlete-events';

  constructor(protected http: HttpClient) {}

  create(athleteEvent: IAthleteEvent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(athleteEvent);
    return this.http
      .post<IAthleteEvent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(athleteEvent: IAthleteEvent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(athleteEvent);
    return this.http
      .put<IAthleteEvent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAthleteEvent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAthleteEvent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(athleteEvent: IAthleteEvent): IAthleteEvent {
    const copy: IAthleteEvent = Object.assign({}, athleteEvent, {
      registrationDate:
        athleteEvent.registrationDate && athleteEvent.registrationDate.isValid() ? athleteEvent.registrationDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.registrationDate = res.body.registrationDate ? moment(res.body.registrationDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((athleteEvent: IAthleteEvent) => {
        athleteEvent.registrationDate = athleteEvent.registrationDate ? moment(athleteEvent.registrationDate) : undefined;
      });
    }
    return res;
  }
}
