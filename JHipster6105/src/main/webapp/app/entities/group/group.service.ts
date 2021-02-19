import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGroup } from 'app/shared/model/group.model';

type EntityResponseType = HttpResponse<IGroup>;
type EntityArrayResponseType = HttpResponse<IGroup[]>;

@Injectable({ providedIn: 'root' })
export class GroupService {
  public resourceUrl = SERVER_API_URL + 'api/groups';

  constructor(protected http: HttpClient) {}

  create(group: IGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(group);
    return this.http
      .post<IGroup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(group: IGroup): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(group);
    return this.http
      .put<IGroup>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGroup>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGroup[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(group: IGroup): IGroup {
    const copy: IGroup = Object.assign({}, group, {
      created: group.created && group.created.isValid() ? group.created.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.created = res.body.created ? moment(res.body.created) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((group: IGroup) => {
        group.created = group.created ? moment(group.created) : undefined;
      });
    }
    return res;
  }
}
