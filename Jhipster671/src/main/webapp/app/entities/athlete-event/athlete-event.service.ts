import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

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
    return this.http.post<IAthleteEvent>(this.resourceUrl, athleteEvent, { observe: 'response' });
  }

  update(athleteEvent: IAthleteEvent): Observable<EntityResponseType> {
    return this.http.put<IAthleteEvent>(this.resourceUrl, athleteEvent, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAthleteEvent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAthleteEvent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
