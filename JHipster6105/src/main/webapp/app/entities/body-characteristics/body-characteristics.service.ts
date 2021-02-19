import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBodyCharacteristics } from 'app/shared/model/body-characteristics.model';

type EntityResponseType = HttpResponse<IBodyCharacteristics>;
type EntityArrayResponseType = HttpResponse<IBodyCharacteristics[]>;

@Injectable({ providedIn: 'root' })
export class BodyCharacteristicsService {
  public resourceUrl = SERVER_API_URL + 'api/body-characteristics';

  constructor(protected http: HttpClient) {}

  create(bodyCharacteristics: IBodyCharacteristics): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bodyCharacteristics);
    return this.http
      .post<IBodyCharacteristics>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(bodyCharacteristics: IBodyCharacteristics): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(bodyCharacteristics);
    return this.http
      .put<IBodyCharacteristics>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IBodyCharacteristics>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IBodyCharacteristics[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(bodyCharacteristics: IBodyCharacteristics): IBodyCharacteristics {
    const copy: IBodyCharacteristics = Object.assign({}, bodyCharacteristics, {
      date: bodyCharacteristics.date && bodyCharacteristics.date.isValid() ? bodyCharacteristics.date.toJSON() : undefined,
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
      res.body.forEach((bodyCharacteristics: IBodyCharacteristics) => {
        bodyCharacteristics.date = bodyCharacteristics.date ? moment(bodyCharacteristics.date) : undefined;
      });
    }
    return res;
  }
}
