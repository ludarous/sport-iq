import { Component, OnInit } from '@angular/core';
import { EventService } from '../../dashboard/services/rest/event.service';
import { IEvent } from '../../dashboard/entities/model/event.model';
import { Moment } from 'moment';
import * as moment from 'moment';
import { IEventLocation } from '../../dashboard/entities/model/event-location.model';
import { AuthService } from '../../modules/auth/services/auth.service';
import { IUser } from '../../modules/entities/user';
import { AuthUtils } from '../../modules/core/utils/auth.utils';
import { HttpResponse } from '@angular/common/http';
import { even } from '@rxweb/reactive-form-validators';

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
    currentUser: IUser;

    ngOnInit() {
        this.currentUser = this.authService.getCurrentUser();
        this.load();

        AuthUtils.userUpdated$.subscribe((user: IUser) => {
            this.setUser(user);
        });
    }

    load() {
        const nextEvents$ = this.eventService.query();
        nextEvents$.subscribe((nextEventsResponse) => {
            this.nextEvents = nextEventsResponse.body; // nextEventsResponse.body.filter(e => e.date.isAfter(this.now));
        });
    }

    alreadySignedIn(event: IEvent): boolean {
        if (this.currentUser) {
            return event.athletes.some(a => a.userId === this.currentUser.id);
        }
        return false;
    }

    getMapLink(eventLocation: IEventLocation) {
        return eventLocation.mapLink;
    }

    signToEvent(event: IEvent) {
        if (this.currentUser) {
            this.eventService.signToEvent(event.id).subscribe((eventResponse: HttpResponse<IEvent>) => {
               this.load();
            });
        }
    }

    setUser(user: IUser) {
        this.currentUser = user;
    }

    signIn() {
        this.authService.login();
    }

    register() {

    }
}
