import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { RxjsUtils } from '../../../modules/core/utils/rxjs.utils';
import { EventService } from '../../../services/rest/event.service';
import { Event, IEvent } from '../../../entities/model/event.model';
import { CalendarUtils } from '../../../modules/core/utils/calendar-utils';
import { WorkoutService } from '../../../services/rest/workout.service';
import { AthleteService } from '../../../services/rest/athlete.service';

@Component({
    selector: 'app-activity-categories-edit',
    templateUrl: './events-results.component.html',
    styleUrls: ['./events-results.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EventsResultsComponent implements OnInit {

    constructor(private eventService: EventService,
                private workoutService: WorkoutService,
                private athleteService: AthleteService,
                private activatedRoute: ActivatedRoute) {
    }

    eventId: number;
    event: IEvent;

    csLocale = CalendarUtils.calendarLocale.cs;
    enLocale: any;

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

}
