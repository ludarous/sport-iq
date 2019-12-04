import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, zip} from 'rxjs';
import {catchError, flatMap, map} from 'rxjs/operators';
import {RxjsUtils} from '../../../modules/core/utils/rxjs.utils';
import {EventService} from '../../../services/rest/event.service';
import {Event, IEvent} from '../../../entities/model/event.model';
import {CalendarUtils} from '../../../modules/core/utils/calendar-utils';
import {WorkoutService} from '../../../services/rest/workout.service';
import {AthleteService} from '../../../services/rest/athlete.service';
import {IWorkout} from '../../../entities/model/workout.model';
import {IActivity} from '../../../entities/model/activity.model';
import {MenuItem, TreeNode} from 'primeng/api';
import {IAthlete} from '../../../entities/model/athlete.model';
import {AthleteEvent, IAthleteEvent} from '../../../entities/model/athlete-event.model';
import {AthleteEventService} from '../../../services/rest/athlete-event.service';
import {Location} from '@angular/common';
import {IAthleteWorkout} from '../../../entities/model/athlete-workout.model';
import {IAthleteActivity} from '../../../entities/model/athlete-activity.model';
import {AthleteWorkoutService} from '../../../services/rest/athlete-workout.service';

@Component({
    selector: 'app-events-results',
    templateUrl: './events-results.component.html',
    styleUrls: ['./events-results.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EventsResultsComponent implements OnInit {

    constructor(private eventService: EventService,
                private athleteEventService: AthleteEventService,
                private athleteWorkoutService: AthleteWorkoutService,
                private workoutService: WorkoutService,
                private athleteService: AthleteService,
                private activatedRoute: ActivatedRoute,
                private router: Router,
                private location: Location) {
    }

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

    csLocale = CalendarUtils.calendarLocale.cs;
    enLocale: any;

    menuItems: Array<MenuItem> = new Array<MenuItem>();
    workoutTreeNodes: Array<TreeNode> = new Array<TreeNode>();
    athletesTreeNodes: Array<TreeNode> = new Array<TreeNode>();
    selectedWorkoutNode: TreeNode;
    selectedAthleteNode: TreeNode;

    ngOnInit() {

        const params$ = this.activatedRoute.params;
        params$.subscribe((params) => {

            this.eventId = +params.id;
            this.selectedAthleteId = +params.athleteId;
            this.selectedWorkoutId = +params.workoutId;
            this.selectedActivityId = +params.activityId;

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

        });
    }

    selectAthlete(athlete: IAthlete) {
        if (athlete === null) {
            this.selectedAthlete = null;
            this.location.go(`/events/results/${this.eventId}`);
        } else {
            this.selectedAthlete = athlete;
            this.athleteEventService
                .getAthleteEvent(this.event.id, this.selectedAthlete.id)
                .subscribe((athleteEvent: IAthleteEvent) => {
                    this.selectedAthleteEvent = athleteEvent;
                    this.location.go(`/events/results/${this.eventId}/athlete/${this.selectedAthlete.id}`);
                });

        }
    }

    selectWorkout(workout: IWorkout) {
        if (workout === null) {
            this.selectedWorkout = null;
            this.selectAthlete(this.selectedAthlete);
        } else {
            this.selectedWorkout = workout;
            this.athleteWorkoutService
                .getAthleteWorkout(this.selectedWorkout.id, this.selectedAthleteEvent.id)
                .subscribe((athleteWorkout: IAthleteWorkout) => {
                    this.selectedAthleteWorkout = athleteWorkout;
                    this.location.go(`/events/results/${this.eventId}/athlete/${this.selectedAthlete.id}/workout/${this.selectedWorkout.id}`);
                });
        }
    }

    selectActivity(activity: IActivity) {
        if (activity === null) {
            this.selectedActivity = null;
            this.selectWorkout(this.selectedWorkout);
        } else {
            this.selectedActivity = activity;
            this.location.go(`/events/results/${this.eventId}/athlete/${this.selectedAthlete.id}/workout/${this.selectedWorkout.id}/activity/${this.selectedActivity.id}`);
        }
    }
}
