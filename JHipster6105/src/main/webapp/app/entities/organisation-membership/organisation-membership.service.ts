import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IOrganisationMembership } from 'app/shared/model/organisation-membership.model';

type EntityResponseType = HttpResponse<IOrganisationMembership>;
type EntityArrayResponseType = HttpResponse<IOrganisationMembership[]>;

@Injectable({ providedIn: 'root' })
export class OrganisationMembershipService {
  public resourceUrl = SERVER_API_URL + 'api/organisation-memberships';

  constructor(protected http: HttpClient) {}

  create(organisationMembership: IOrganisationMembership): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organisationMembership);
    return this.http
      .post<IOrganisationMembership>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(organisationMembership: IOrganisationMembership): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(organisationMembership);
    return this.http
      .put<IOrganisationMembership>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOrganisationMembership>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOrganisationMembership[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(organisationMembership: IOrganisationMembership): IOrganisationMembership {
    const copy: IOrganisationMembership = Object.assign({}, organisationMembership, {
      created:
        organisationMembership.created && organisationMembership.created.isValid() ? organisationMembership.created.toJSON() : undefined,
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
      res.body.forEach((organisationMembership: IOrganisationMembership) => {
        organisationMembership.created = organisationMembership.created ? moment(organisationMembership.created) : undefined;
      });
    }
    return res;
  }
}
