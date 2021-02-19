import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMembershipRole } from 'app/shared/model/membership-role.model';

type EntityResponseType = HttpResponse<IMembershipRole>;
type EntityArrayResponseType = HttpResponse<IMembershipRole[]>;

@Injectable({ providedIn: 'root' })
export class MembershipRoleService {
  public resourceUrl = SERVER_API_URL + 'api/membership-roles';

  constructor(protected http: HttpClient) {}

  create(membershipRole: IMembershipRole): Observable<EntityResponseType> {
    return this.http.post<IMembershipRole>(this.resourceUrl, membershipRole, { observe: 'response' });
  }

  update(membershipRole: IMembershipRole): Observable<EntityResponseType> {
    return this.http.put<IMembershipRole>(this.resourceUrl, membershipRole, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IMembershipRole>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IMembershipRole[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
