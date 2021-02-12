import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IUserProperties } from 'app/shared/model/user-properties.model';

type EntityResponseType = HttpResponse<IUserProperties>;
type EntityArrayResponseType = HttpResponse<IUserProperties[]>;

@Injectable({ providedIn: 'root' })
export class UserPropertiesService {
  public resourceUrl = SERVER_API_URL + 'api/user-properties';

  constructor(protected http: HttpClient) {}

  create(userProperties: IUserProperties): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userProperties);
    return this.http
      .post<IUserProperties>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(userProperties: IUserProperties): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(userProperties);
    return this.http
      .put<IUserProperties>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IUserProperties>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IUserProperties[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(userProperties: IUserProperties): IUserProperties {
    const copy: IUserProperties = Object.assign({}, userProperties, {
      birthDate: userProperties.birthDate && userProperties.birthDate.isValid() ? userProperties.birthDate.toJSON() : undefined,
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
      res.body.forEach((userProperties: IUserProperties) => {
        userProperties.birthDate = userProperties.birthDate ? moment(userProperties.birthDate) : undefined;
      });
    }
    return res;
  }
}
