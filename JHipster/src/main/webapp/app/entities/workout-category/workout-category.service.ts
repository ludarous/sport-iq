import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IWorkoutCategory } from 'app/shared/model/workout-category.model';

type EntityResponseType = HttpResponse<IWorkoutCategory>;
type EntityArrayResponseType = HttpResponse<IWorkoutCategory[]>;

@Injectable({ providedIn: 'root' })
export class WorkoutCategoryService {
    private resourceUrl = SERVER_API_URL + 'api/workout-categories';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/workout-categories';

    constructor(private http: HttpClient) {}

    create(workoutCategory: IWorkoutCategory): Observable<EntityResponseType> {
        return this.http.post<IWorkoutCategory>(this.resourceUrl, workoutCategory, { observe: 'response' });
    }

    update(workoutCategory: IWorkoutCategory): Observable<EntityResponseType> {
        return this.http.put<IWorkoutCategory>(this.resourceUrl, workoutCategory, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IWorkoutCategory>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IWorkoutCategory[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IWorkoutCategory[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
