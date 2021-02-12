import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ILegalRepresentative } from 'app/shared/model/legal-representative.model';

type EntityResponseType = HttpResponse<ILegalRepresentative>;
type EntityArrayResponseType = HttpResponse<ILegalRepresentative[]>;

@Injectable({ providedIn: 'root' })
export class LegalRepresentativeService {
  public resourceUrl = SERVER_API_URL + 'api/legal-representatives';

  constructor(protected http: HttpClient) {}

  create(legalRepresentative: ILegalRepresentative): Observable<EntityResponseType> {
    return this.http.post<ILegalRepresentative>(this.resourceUrl, legalRepresentative, { observe: 'response' });
  }

  update(legalRepresentative: ILegalRepresentative): Observable<EntityResponseType> {
    return this.http.put<ILegalRepresentative>(this.resourceUrl, legalRepresentative, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ILegalRepresentative>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILegalRepresentative[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
