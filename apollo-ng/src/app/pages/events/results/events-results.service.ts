import {Injectable} from '@angular/core';
import {IAthlete} from '../../../entities/model/athlete.model';
import {IAthleteEvent} from '../../../entities/model/athlete-event.model';
import {IWorkout} from '../../../entities/model/workout.model';
import {IAthleteWorkout} from '../../../entities/model/athlete-workout.model';
import {IActivity} from '../../../entities/model/activity.model';
import {IAthleteActivity} from '../../../entities/model/athlete-activity.model';
import {IEvent} from '../../../entities/model/event.model';
import {EventService} from '../../../services/rest/event.service';
import {AthleteEventService} from '../../../services/rest/athlete-event.service';
import {AthleteWorkoutService} from '../../../services/rest/athlete-workout.service';
import {Location} from '@angular/common';
import {AthleteActivityService} from '../../../services/rest/athlete-activity.service';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ToastService} from '../../../modules/core/services/message.service';
import {IAthleteActivityResult} from '../../../entities/model/athlete-activity-result.model';
import {IActivityResult} from '../../../entities/model/activity-result.model';
import {Observable, Subject} from 'rxjs';
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
    selectedAthleteEvent: IAthleteEvent;

    selectedWorkout: IWorkout = null;
    selectedWorkoutId: number;
    selectedAthleteWorkout: IAthleteWorkout;

    selectedActivity: IActivity = null;
    selectedActivityId: number;
    selectedAthleteActivity: IAthleteActivity;

    selectedAthlete: IAthlete = null;
    selectedAthleteId: number;

    init(eventId: number, athleteId?: number, workoutId?: number, activityId?: number) {

        this.eventId = eventId;
        this.selectedAthleteId = athleteId;
        this.selectedWorkoutId = workoutId;
        this.selectedActivityId = activityId;

        const getEvent$ = this.eventService.getEvent(this.eventId);

        getEvent$.subscribe((event: IEvent) => {
            this.event = event;
            if (this.selectedAthleteId) {
                const athlete = this.event.athletes.find(a => a.id === this.selectedAthleteId);
                this.selectAthlete(athlete);
            }

            if (this.selectedWorkoutId) {
                const workout = this.event.tests.find(w => w.id === this.selectedWorkoutId);
                this.selectWorkout(workout);
            }

            if (this.selectedActivityId && this.selectedWorkout) {
                const activity = this.selectedWorkout.activities.find(a => a.id === this.selectedActivityId);
                this.selectActivity(activity);
            }
        });
    }

    selectAthlete(athlete: IAthlete) {
        this.saveAll();
        if (athlete === null) {
            this.selectedAthlete = null;
            this.location.go(`/events/results/${this.eventId}`);
        } else {
            this.athleteEventService
                .getAthleteEvent(this.event.id, athlete.id)
                .subscribe((athleteEvent: IAthleteEvent) => {
                    this.selectedAthlete = athlete;
                    this.selectedAthleteEvent = athleteEvent;
                    this.selectedAthleteEventChangeSource.next(this.selectedAthleteEvent);
                    this.selectWorkout(this.selectedWorkout);

                    this.location.go(`/events/results/${this.eventId}/athlete/${this.selectedAthlete.id}`);
                });

        }
    }

    selectWorkout(workout: IWorkout) {
        this.saveAll();
        if (workout === null) {
            this.selectedWorkout = null;
        } else {
            this.athleteWorkoutService
                .getAthleteWorkout(workout.id, this.selectedAthleteEvent.id)
                .subscribe((athleteWorkout: IAthleteWorkout) => {
                    this.selectedAthleteWorkout = athleteWorkout;
                    this.selectedWorkout = workout;
                    this.selectedAthleteWorkoutChangeSource.next(this.selectedAthleteWorkout);
                    this.selectActivity(this.selectedActivity);

                    this.location.go(`/events/results/${this.eventId}/athlete/${this.selectedAthlete.id}/workout/${this.selectedWorkout.id}`);
                });
        }
    }

    selectActivity(activity: IActivity) {
        this.saveAll();
        if (activity === null) {
            this.selectedActivity = null;
        } else {

            this.athleteActivityService
                .getAthleteActivity(activity.id, this.selectedAthleteWorkout.id)
                .subscribe((athleteActivity: IAthleteActivity) => {
                    this.selectedAthleteActivity = athleteActivity;
                    this.selectedActivity = activity;
                    this.selectedAthleteActivityChangeSource.next(this.selectedAthleteActivity);
                    this.location.go(`/events/results/${this.eventId}/athlete/${this.selectedAthlete.id}/workout/${this.selectedWorkout.id}/activity/${this.selectedActivity.id}`);
                });
        }
    }

    showAthleteEventForm(): boolean {
        return !!this.selectedAthlete &&
            !!this.selectedAthleteEvent &&
            !this.showAthleteWorkoutForm() &&
            !this.showAthleteActivityForm();
    }

    showAthleteWorkoutForm(): boolean {
        return !!this.selectedWorkout && !!this.selectedAthleteEvent && !!this.selectedAthleteWorkout && !this.showAthleteActivityForm();
    }

    showAthleteActivityForm(): boolean {
        return !!this.selectedActivity && !!this.selectedAthleteActivity && !!this.selectedAthleteWorkout;
    }

    getActivityResult(athleteActivityResult: IAthleteActivityResult): IActivityResult {
        const activityResult = this.selectedActivity.activityResults.find(ar => ar.id === athleteActivityResult.activityResultId);
        return activityResult;
    }

    saveAll() {
        if (this.selectedAthleteEvent) {
            this.saveAthleteEvent(this.selectedAthleteEvent).subscribe();
        }

        if (this.selectedAthleteWorkout) {
            this.saveAthleteWorkout(this.selectedAthleteWorkout).subscribe();
        }

        if (this.selectedActivity) {
            this.saveAthleteActivity();
        }
    }

    saveAthleteActivity(): Observable<IAthleteActivity> {
        const athleteActivityToSave = this.selectedAthleteActivity;

        let saveAthleteActivity$;
        if (athleteActivityToSave.id) {
            saveAthleteActivity$ = this.athleteActivityService.update(athleteActivityToSave);
        } else {
            saveAthleteActivity$ = this.athleteActivityService.create(athleteActivityToSave);
        }

        return saveAthleteActivity$.pipe(map(
            (athleteActivityResponse: HttpResponse<IAthleteActivity>) => {
                this.selectedAthleteActivity = athleteActivityResponse.body;
                this.toastService.showSuccess('Výsledky aktivity uloženy');
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
                return athleteWorkoutResponse;
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
                return athleteEventResponse;
            }),
            catchError((errorResponse: HttpErrorResponse, caught) => {
                this.toastService.showError('Událost nebyla uložena', errorResponse.error.detail);
                return null;
            }));

    }
}
