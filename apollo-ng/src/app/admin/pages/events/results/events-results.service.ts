import {Injectable} from '@angular/core';
import {IAthlete} from '../../../entities/model/athlete.model';
import {AthleteEvent, IAthleteEvent} from '../../../entities/model/athlete-event.model';
import {IWorkout} from '../../../entities/model/workout.model';
import {AthleteWorkout, IAthleteWorkout} from '../../../entities/model/athlete-workout.model';
import {IActivity} from '../../../entities/model/activity.model';
import {IAthleteActivity} from '../../../entities/model/athlete-activity.model';
import {IEvent} from '../../../entities/model/event.model';
import {EventService} from '../../../services/rest/event.service';
import {AthleteEventService} from '../../../services/rest/athlete-event.service';
import {AthleteWorkoutService} from '../../../services/rest/athlete-workout.service';
import {Location} from '@angular/common';
import {AthleteActivityService} from '../../../services/rest/athlete-activity.service';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ToastService} from '../../../../modules/core/services/message.service';
import {IAthleteActivityResult} from '../../../entities/model/athlete-activity-result.model';
import {IActivityResult} from '../../../entities/model/activity-result.model';
import {Observable, Subject, zip} from 'rxjs';
import {catchError, map} from 'rxjs/operators';

@Injectable()
export class EventResultsService {

    constructor(private eventService: EventService,
                private athleteEventService: AthleteEventService,
                private athleteWorkoutService: AthleteWorkoutService,
                private athleteActivityService: AthleteActivityService,
                private location: Location,
                private toastService: ToastService) {

    }

    public selectedAthleteEventChangeSource = new Subject<IAthleteEvent>();
    public selectedAthleteEventChange$ = this.selectedAthleteEventChangeSource.asObservable();

    public selectedAthleteWorkoutChangeSource = new Subject<IAthleteWorkout>();
    public selectedAthleteWorkoutChange$ = this.selectedAthleteWorkoutChangeSource.asObservable();

    public selectedAthleteActivityChangeSource = new Subject<IAthleteActivity>();
    public selectedAthleteActivityChange$ = this.selectedAthleteActivityChangeSource.asObservable();

    eventId: number;
    event: IEvent;
    allAthleteEvents: Array<IAthleteEvent>;

    selectedWorkout: IWorkout = null;
    selectedActivity: IActivity = null;

    athleteEventAthleteMap: Map<IAthleteEvent, IAthlete> = new Map<IAthleteEvent, IAthlete>();
    athleteWorkoutAthleteMap: Map<IAthleteWorkout, IAthlete> = new Map<IAthleteWorkout, IAthlete>();
    athleteActivityAthleteMap: Map<IAthleteActivity, IAthlete> = new Map<IAthleteActivity, IAthlete>();

    athleteWorkoutWorkoutMap: Map<IAthleteWorkout, IWorkout> = new Map<IAthleteWorkout, IWorkout>();
    athleteActivityActivityMap: Map<IAthleteActivity, IActivity> = new Map<IAthleteActivity, IActivity>();

    init(eventId: number, athleteId?: number, workoutId?: number, activityId?: number) {

        this.eventId = eventId;

        const getEvent$ = this.eventService.getEvent(this.eventId);
        const getAthleteEvents$ = this.athleteEventService.getAthleteEventsByEventId(this.eventId);

        zip(getEvent$, getAthleteEvents$).subscribe(([event, athleteEvents]) => {
            this.event = event;
            this.allAthleteEvents = athleteEvents.body;
            this.setMaps();
        });
    }

    setMaps() {
        for (const athleteEvent of this.allAthleteEvents) {
            const athlete = this.event.athletes.find(a => a.id === athleteEvent.athleteId);
            this.athleteEventAthleteMap.set(athleteEvent, athlete);

            for (const athleteWorkout of athleteEvent.athleteWorkouts) {
                const workout = this.event.tests.find(w => w.id === athleteWorkout.workoutId);
                this.athleteWorkoutAthleteMap.set(athleteWorkout, athlete);
                this.athleteWorkoutWorkoutMap.set(athleteWorkout, workout);

                for (const athleteActivity of athleteWorkout.athleteActivities) {
                    const activity = workout.activities.find(a => a.id === athleteActivity.activityId);
                    this.athleteActivityAthleteMap.set(athleteActivity, athlete);
                    this.athleteActivityActivityMap.set(athleteActivity, activity);
                }
            }
        }
    }


    selectActivity(activity: IActivity, workout: IWorkout) {
        this.selectedActivity = activity;
        this.selectedWorkout = workout;
    }

    getAthleteForAthleteEvent(athleteEvent: IAthleteEvent): IAthlete {
        return this.athleteEventAthleteMap.get(athleteEvent);
    }

    getAthleteForAthleteWorkout(athleteWorkout: IAthleteWorkout): IAthlete {
        return this.athleteWorkoutAthleteMap.get(athleteWorkout);
    }

    getAthleteForAthleteActivity(athleteActivity: IAthleteActivity): IAthlete {
        return this.athleteActivityAthleteMap.get(athleteActivity);
    }

    getAthleteWorkouts(): Array<IAthleteWorkout> {
        const selectedAthleteWorkouts = new Array<IAthleteWorkout>();
        if (this.selectedWorkout) {
            for (const athleteEvent of this.allAthleteEvents) {
                selectedAthleteWorkouts.push(athleteEvent.athleteWorkouts.find(aw => aw.workoutId === this.selectedWorkout.id));
            }
        }
        return selectedAthleteWorkouts;
    }

    getAthleteActivities(): Array<IAthleteActivity> {
        const selectedAthleteActivities = new Array<IAthleteActivity>();
        if (this.getAthleteWorkouts().length > 0 && this.selectedActivity) {
            for (const athleteWorkout of this.getAthleteWorkouts()) {
                selectedAthleteActivities.push(athleteWorkout.athleteActivities.find(aa => aa.activityId === this.selectedActivity.id));
            }
        }
        return selectedAthleteActivities;
    }


    getActivityResult(athleteActivityResult: IAthleteActivityResult): IActivityResult {
        const activityResult = this.selectedActivity.activityResults.find(ar => ar.id === athleteActivityResult.activityResultId);
        return activityResult;
    }


    saveAthleteActivity(athleteActivityToSave: IAthleteActivity): Observable<IAthleteActivity> {

        let saveAthleteActivity$;
        if (athleteActivityToSave.id) {
            saveAthleteActivity$ = this.athleteActivityService.update(athleteActivityToSave);
        } else {
            saveAthleteActivity$ = this.athleteActivityService.create(athleteActivityToSave);
        }

        return saveAthleteActivity$.pipe(map(
            (athleteActivityResponse: HttpResponse<IAthleteActivity>) => {
                this.toastService.showSuccess('Výsledky aktivity uloženy');
                return athleteActivityResponse.body;
            }),
            catchError((errorResponse: HttpErrorResponse, caught) => {
                this.toastService.showError('Výsledky aktivity nebyly uložena', errorResponse.error.detail);
                return null;
            }));
    }

    saveAthleteWorkout(athleteWorkoutToSave: IAthleteWorkout): Observable<IAthleteWorkout> {
        let saveAthleteWorkout$;
        if (athleteWorkoutToSave.id) {
            saveAthleteWorkout$ = this.athleteWorkoutService.update(athleteWorkoutToSave);
        } else {
            saveAthleteWorkout$ = this.athleteWorkoutService.create(athleteWorkoutToSave);
        }


        return saveAthleteWorkout$.pipe(map
            ((athleteWorkoutResponse: HttpResponse<IAthleteWorkout>) => {
                this.toastService.showSuccess('Obecné informace k testu uloženy');
                return athleteWorkoutResponse.body;
            }),
            catchError((errorResponse: HttpErrorResponse, caught) => {
                this.toastService.showError('Obecné informace k testu uloženy', errorResponse.error.detail);
                return null;
            }));
    }

    saveAthleteEvent(athleteEventToSave: IAthleteEvent): Observable<IAthleteEvent> {

        let saveAthleteEvent$;
        if (athleteEventToSave.id) {
            saveAthleteEvent$ = this.athleteEventService.update(athleteEventToSave);
        } else {
            saveAthleteEvent$ = this.athleteEventService.create(athleteEventToSave);
        }

        return saveAthleteEvent$.pipe(map(
            (athleteEventResponse: HttpResponse<IAthleteEvent>) => {
                this.toastService.showSuccess('Událost uložena');
                return athleteEventResponse.body;
            }),
            catchError((errorResponse: HttpErrorResponse, caught) => {
                this.toastService.showError('Událost nebyla uložena', errorResponse.error.detail);
                return null;
            }));

    }
}
