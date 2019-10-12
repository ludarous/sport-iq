import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IActivityResult } from 'app/shared/model/activity-result.model';

type EntityResponseType = HttpResponse<IActivityResult>;
type EntityArrayResponseType = HttpResponse<IActivityResult[]>;

@Injectable({ providedIn: 'root' })
export class ActivityResultService {
    private resourceUrl = SERVER_API_URL + 'api/activity-results';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/activity-results';

    constructor(private http: HttpClient) {}

    create(activityResult: IActivityResult): Observable<EntityResponseType> {
        return this.http.post<IActivityResult>(this.resourceUrl, activityResult, { observe: 'response' });
    }

    update(activityResult: IActivityResult): Observable<EntityResponseType> {
        return this.http.put<IActivityResult>(this.resourceUrl, activityResult, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IActivityResult>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IActivityResult[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IActivityResult[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
