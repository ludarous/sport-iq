import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';

type EntityResponseType = HttpResponse<IAthleteActivityResult>;
type EntityArrayResponseType = HttpResponse<IAthleteActivityResult[]>;

@Injectable({ providedIn: 'root' })
export class AthleteActivityResultService {
    private resourceUrl = SERVER_API_URL + 'api/athlete-activity-results';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/athlete-activity-results';

    constructor(private http: HttpClient) {}

    create(athleteActivityResult: IAthleteActivityResult): Observable<EntityResponseType> {
        return this.http.post<IAthleteActivityResult>(this.resourceUrl, athleteActivityResult, { observe: 'response' });
    }

    update(athleteActivityResult: IAthleteActivityResult): Observable<EntityResponseType> {
        return this.http.put<IAthleteActivityResult>(this.resourceUrl, athleteActivityResult, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAthleteActivityResult>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAthleteActivityResult[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAthleteActivityResult[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
