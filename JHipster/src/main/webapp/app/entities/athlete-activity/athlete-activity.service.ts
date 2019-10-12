import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAthleteActivity } from 'app/shared/model/athlete-activity.model';

type EntityResponseType = HttpResponse<IAthleteActivity>;
type EntityArrayResponseType = HttpResponse<IAthleteActivity[]>;

@Injectable({ providedIn: 'root' })
export class AthleteActivityService {
    private resourceUrl = SERVER_API_URL + 'api/athlete-activities';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/athlete-activities';

    constructor(private http: HttpClient) {}

    create(athleteActivity: IAthleteActivity): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(athleteActivity);
        return this.http
            .post<IAthleteActivity>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(athleteActivity: IAthleteActivity): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(athleteActivity);
        return this.http
            .put<IAthleteActivity>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IAthleteActivity>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAthleteActivity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IAthleteActivity[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    private convertDateFromClient(athleteActivity: IAthleteActivity): IAthleteActivity {
        const copy: IAthleteActivity = Object.assign({}, athleteActivity, {
            date: athleteActivity.date != null && athleteActivity.date.isValid() ? athleteActivity.date.toJSON() : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.date = res.body.date != null ? moment(res.body.date) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((athleteActivity: IAthleteActivity) => {
            athleteActivity.date = athleteActivity.date != null ? moment(athleteActivity.date) : null;
        });
        return res;
    }
}
