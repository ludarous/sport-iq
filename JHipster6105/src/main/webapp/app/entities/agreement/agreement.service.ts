import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAgreement } from 'app/shared/model/agreement.model';

type EntityResponseType = HttpResponse<IAgreement>;
type EntityArrayResponseType = HttpResponse<IAgreement[]>;

@Injectable({ providedIn: 'root' })
export class AgreementService {
  public resourceUrl = SERVER_API_URL + 'api/agreements';

  constructor(protected http: HttpClient) {}

  create(agreement: IAgreement): Observable<EntityResponseType> {
    return this.http.post<IAgreement>(this.resourceUrl, agreement, { observe: 'response' });
  }

  update(agreement: IAgreement): Observable<EntityResponseType> {
    return this.http.put<IAgreement>(this.resourceUrl, agreement, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAgreement>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAgreement[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
