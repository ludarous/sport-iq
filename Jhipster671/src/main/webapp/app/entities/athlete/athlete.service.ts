import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAthlete } from 'app/shared/model/athlete.model';

type EntityResponseType = HttpResponse<IAthlete>;
type EntityArrayResponseType = HttpResponse<IAthlete[]>;

@Injectable({ providedIn: 'root' })
export class AthleteService {
  public resourceUrl = SERVER_API_URL + 'api/athletes';

  constructor(protected http: HttpClient) {}

  create(athlete: IAthlete): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(athlete);
    return this.http
      .post<IAthlete>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(athlete: IAthlete): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(athlete);
    return this.http
      .put<IAthlete>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAthlete>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAthlete[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(athlete: IAthlete): IAthlete {
    const copy: IAthlete = Object.assign({}, athlete, {
      birthDate: athlete.birthDate && athlete.birthDate.isValid() ? athlete.birthDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.birthDate = res.body.birthDate ? moment(res.body.birthDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((athlete: IAthlete) => {
        athlete.birthDate = athlete.birthDate ? moment(athlete.birthDate) : undefined;
      });
    }
    return res;
  }
}
