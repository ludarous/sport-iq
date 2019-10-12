import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IActivityCategory } from 'app/shared/model/activity-category.model';

type EntityResponseType = HttpResponse<IActivityCategory>;
type EntityArrayResponseType = HttpResponse<IActivityCategory[]>;

@Injectable({ providedIn: 'root' })
export class ActivityCategoryService {
    private resourceUrl = SERVER_API_URL + 'api/activity-categories';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/activity-categories';

    constructor(private http: HttpClient) {}

    create(activityCategory: IActivityCategory): Observable<EntityResponseType> {
        return this.http.post<IActivityCategory>(this.resourceUrl, activityCategory, { observe: 'response' });
    }

    update(activityCategory: IActivityCategory): Observable<EntityResponseType> {
        return this.http.put<IActivityCategory>(this.resourceUrl, activityCategory, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IActivityCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IActivityCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IActivityCategory[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
