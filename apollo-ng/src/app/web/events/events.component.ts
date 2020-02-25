import { Component, OnInit } from '@angular/core';
import { EventService } from '../../admin/services/rest/event.service';
import { IEvent } from '../../admin/entities/model/event.model';
import { Moment } from 'moment';
import * as moment from 'moment';
import { IEventLocation } from '../../admin/entities/model/event-location.model';
import { AuthService } from '../../modules/auth/services/auth.service';

declare const WOW: any;

@Component({
    selector: 'app-events',
    templateUrl: './events.component.html',
    styleUrls: ['./events.component.scss']
})
export class EventsComponent implements OnInit {

    constructor(private eventService: EventService,
                private authService: AuthService) {
    }

    nextEvents: Array<IEvent>;
    now = moment();
    showSingInDialog: any;

    ngOnInit() {
        const nextEvents$ = this.eventService.query();
        nextEvents$.subscribe((nextEventsResponse) => {
            this.nextEvents = nextEventsResponse.body; // nextEventsResponse.body.filter(e => e.date.isAfter(this.now));
        });
    }

    getMapLink(eventLocation: IEventLocation) {
        return eventLocation.mapLink;
    }

    signToEvent(event: IEvent) {
        if (this.authService.getCurrentUser()) {

        } else {
            this.showSingInDialog = true;
        }
    }

    signIn() {
        this.authService.login();
    }

    register() {

    }
}
