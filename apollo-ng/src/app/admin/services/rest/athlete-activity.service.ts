import {Injectable, Injector} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {CrudBaseService} from '../../../modules/auth/services/rest/rest-base.service';
import {Event} from '../../entities/model/event.model';
import {Observable} from 'rxjs';
import {HttpResponse} from '@angular/common/http';
import {catchError, map} from 'rxjs/operators';
import {RxjsUtils} from '../../../modules/core/utils/rxjs.utils';
import {AthleteWorkout, IAthleteWorkout} from '../../entities/model/athlete-workout.model';
import { AthleteActivity, IAthleteActivity } from '../../entities/model/athlete-activity.model';
import { Activity } from '../../entities/model/activity.model';

@Injectable()
export class AthleteActivityService extends CrudBaseService<IAthleteActivity> {
    constructor(injector: Injector) {
        super(environment.apiUrl, '/athlete-activities', injector);
    }

    getAthleteActivityByActivityIdAndAthleteWorkoutId(activityId: number, athleteWorkoutId: number): Observable<HttpResponse<IAthleteActivity>> {
        const params = {activityId, athleteWorkoutId};
        return this.get(this.resourceUrl + `/by-activity-id-and-athleteWorkout-id`, params);
    }

    getAthleteActivity(activityId: number, athleteWorkoutId: number): Observable<IAthleteActivity> {
        if (activityId && athleteWorkoutId) {
            return this.getAthleteActivityByActivityIdAndAthleteWorkoutId(activityId, athleteWorkoutId)
                .pipe(map((eventResponse: HttpResponse<IAthleteWorkout>) => {
                    return eventResponse.body;
                }), catchError((err, caught) => {
                    return RxjsUtils.create(new AthleteActivity(null, null, null, athleteWorkoutId, null, null, activityId));
                }));

        } else {
            return RxjsUtils.create(new AthleteActivity());
        }
    }

}
