import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IGroupMembership } from 'app/shared/model/group-membership.model';

type EntityResponseType = HttpResponse<IGroupMembership>;
type EntityArrayResponseType = HttpResponse<IGroupMembership[]>;

@Injectable({ providedIn: 'root' })
export class GroupMembershipService {
  public resourceUrl = SERVER_API_URL + 'api/group-memberships';

  constructor(protected http: HttpClient) {}

  create(groupMembership: IGroupMembership): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(groupMembership);
    return this.http
      .post<IGroupMembership>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(groupMembership: IGroupMembership): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(groupMembership);
    return this.http
      .put<IGroupMembership>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IGroupMembership>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGroupMembership[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(groupMembership: IGroupMembership): IGroupMembership {
    const copy: IGroupMembership = Object.assign({}, groupMembership, {
      created: groupMembership.created && groupMembership.created.isValid() ? groupMembership.created.toJSON() : undefined,
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
      res.body.forEach((groupMembership: IGroupMembership) => {
        groupMembership.created = groupMembership.created ? moment(groupMembership.created) : undefined;
      });
    }
    return res;
  }
}
