import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import { RxjsUtils } from '../../../modules/core/utils/rxjs.utils';
import { EventService } from '../../../services/rest/event.service';
import { Event, IEvent } from '../../../entities/model/event.model';
import { CalendarUtils } from '../../../modules/core/utils/calendar-utils';
import { WorkoutService } from '../../../services/rest/workout.service';
import { AthleteService } from '../../../services/rest/athlete.service';
import {IWorkout} from '../../../entities/model/workout.model';
import {IActivity} from '../../../entities/model/activity.model';
import {MenuItem, TreeNode} from 'primeng/api';
import {IAthlete} from '../../../entities/model/athlete.model';
import {AthleteEvent, IAthleteEvent} from '../../../entities/model/athlete-event.model';
import {AthleteEventService} from '../../../services/rest/athlete-event.service';

@Component({
    selector: 'app-activity-categories-edit',
    templateUrl: './events-results.component.html',
    styleUrls: ['./events-results.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EventsResultsComponent implements OnInit {

    constructor(private eventService: EventService,
                private athleteEventService: AthleteEventService,
                private workoutService: WorkoutService,
                private athleteService: AthleteService,
                private activatedRoute: ActivatedRoute) {
    }

    eventId: number;
    event: IEvent;

    selectedWorkout: IWorkout;
    selectedActivity: IActivity;
    selectedAthlete: IAthlete;
    selectedAthleteEvent: IAthleteEvent;

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
            const getEvent$ = this.getEvent(this.eventId);

            getEvent$.subscribe((event: IEvent) => {
                this.event = event;
            });
        });
    }

    getEvent(eventId: number): Observable<IEvent> {
        if (eventId) {
            return this.eventService
                .find(eventId)
                .pipe(map((eventResponse: HttpResponse<IEvent>) => {
                    return eventResponse.body;
                }));

        } else {
            return RxjsUtils.create(new Event());
        }
    }

    getAthleteEvent(eventId: number, athleteId: number): Observable<IAthleteEvent> {
        if (eventId && athleteId) {
            return this.athleteEventService
                .getAthleteEventByEventIdAndAthleteId(eventId, athleteId)
                .pipe(map((eventResponse: HttpResponse<IAthleteEvent>) => {
                    return eventResponse.body;
                }), catchError((err, caught) => {
                    return RxjsUtils.create(new AthleteEvent());
                }));

        } else {
            return RxjsUtils.create(new AthleteEvent());
        }
    }

    selectAthlete(athlete: IAthlete) {
        if (this.selectedAthlete === athlete) {
            this.selectedAthlete = null;
        } else {
            this.selectedAthlete = athlete;
            this.getAthleteEvent(this.eventId, athlete.id).subscribe((athleteEvent: IAthleteEvent) => {
                this.selectedAthleteEvent = athleteEvent;
            });
        }
    }

    selectWorkout(workout: IWorkout) {
        if (this.selectedWorkout === workout) {
            this.selectedWorkout = null;
        } else {
            this.selectedWorkout = workout;
        }
    }

    selectActivity(activity: IActivity) {
        if (this.selectedActivity === activity) {
            this.selectedActivity = null;
        } else {
            this.selectedActivity = activity;
        }
    }
}
