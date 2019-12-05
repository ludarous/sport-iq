import {Injectable, Injector} from '@angular/core';
import {environment} from '../../../environments/environment';
import {CrudBaseService} from '../../modules/auth/services/rest/rest-base.service';
import {Event} from '../../entities/model/event.model';
import {Observable} from 'rxjs';
import {HttpResponse} from '@angular/common/http';
import {catchError, map} from 'rxjs/operators';
import {RxjsUtils} from '../../modules/core/utils/rxjs.utils';
import {AthleteWorkout, IAthleteWorkout} from '../../entities/model/athlete-workout.model';

@Injectable()
export class AthleteWorkoutService extends CrudBaseService<IAthleteWorkout> {
    constructor(injector: Injector) {
        super(environment.apiUrl, '/athlete-workouts', injector, [Event.parseItemEnums]);
    }

    getAthleteWorkoutByWorkoutIdAndAthleteEventId(workoutId: number, athleteEventId: number): Observable<HttpResponse<IAthleteWorkout>> {
        const params = {workoutId, athleteEventId};
        return this.get(this.resourceUrl + `/by-workout-id-and-athleteEvent-id`, params);
    }

    getAthleteWorkout(workoutId: number, athleteEventId: number): Observable<IAthleteWorkout> {
        if (workoutId && athleteEventId) {
            return this.getAthleteWorkoutByWorkoutIdAndAthleteEventId(workoutId, athleteEventId)
                .pipe(map((eventResponse: HttpResponse<IAthleteWorkout>) => {
                    return eventResponse.body;
                }), catchError((err, caught) => {
                    return RxjsUtils.create(new AthleteWorkout(null, null, athleteEventId, null, null, workoutId));
                }));

        } else {
            return RxjsUtils.create(new AthleteWorkout());
        }
    }

}
