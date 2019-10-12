import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAthleteWorkout } from 'app/shared/model/athlete-workout.model';

type EntityResponseType = HttpResponse<IAthleteWorkout>;
type EntityArrayResponseType = HttpResponse<IAthleteWorkout[]>;

@Injectable({ providedIn: 'root' })
export class AthleteWorkoutService {
    private resourceUrl = SERVER_API_URL + 'api/athlete-workouts';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/athlete-workouts';

    constructor(private http: HttpClient) {}

    create(athleteWorkout: IAthleteWorkout): Observable<EntityResponseType> {
        return this.http.post<IAthleteWorkout>(this.resourceUrl, athleteWorkout, { observe: 'response' });
    }

    update(athleteWorkout: IAthleteWorkout): Observable<EntityResponseType> {
        return this.http.put<IAthleteWorkout>(this.resourceUrl, athleteWorkout, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAthleteWorkout>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAthleteWorkout[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAthleteWorkout[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}