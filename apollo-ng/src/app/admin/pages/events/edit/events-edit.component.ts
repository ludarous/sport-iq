import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {zip} from 'rxjs';
import {ToastService} from '../../../../modules/core/services/message.service';
import {EventService} from '../../../services/rest/event.service';
import {IEvent} from '../../../entities/model/event.model';
import {CalendarUtils} from '../../../../modules/core/utils/calendar-utils';
import * as moment from 'moment';
import {EnumTranslatorService} from '../../../../modules/shared-components/services/enum-translator.service';
import {Location} from '@angular/common';
import {IWorkout} from '../../../entities/model/workout.model';
import {WorkoutService} from '../../../services/rest/workout.service';
import {AthleteService} from '../../../services/rest/athlete.service';
import {IAthlete} from '../../../entities/model/athlete.model';
import { EventLocationService } from '../../../services/rest/event-location.service';
import { IEventLocation } from '../../../entities/model/event-location.model';

@Component({
    selector: 'app-events-edit',
    templateUrl: './events-edit.component.html',
    styleUrls: ['./events-edit.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EventsEditComponent implements OnInit {

    constructor(private eventService: EventService,
                private eventLocationService: EventLocationService,
                private workoutService: WorkoutService,
                private athleteService: AthleteService,
                private activatedRoute: ActivatedRoute,
                private toastService: ToastService,
                private enumTranslateService: EnumTranslatorService,
                private formBuilder: FormBuilder,
                private router: Router,
                private location: Location) {
    }

    eventId: number;
    event: IEvent;
    eventForm: FormGroup;

    csLocale = CalendarUtils.calendarLocale.cs;
    enLocale: any;

    date: Date;

    allWorkouts: Array<IWorkout>;
    suggestedWorkouts: Array<IWorkout>;

    allAthletes: Array<IAthlete>;
    suggestedAthletes: Array<IAthlete>;

    allEventLocations: Array<IEventLocation>;
    get eventLocationOptions(): Array<any> {
        return this.allEventLocations.map(el => {
            return {
                label: el.name,
                value: el.id
            };
        });
    }

    ngOnInit() {

        const params$ = this.activatedRoute.params;
        params$.subscribe((params) => {
            this.eventId = +params.id;
            const getEvent$ = this.eventService.getEvent(this.eventId);

            const getWorkouts$ = this.workoutService.query();
            const getAthletes$ = this.athleteService.query();
            const getEventLocations$ = this.eventLocationService.query();

            zip(getEvent$, getWorkouts$, getAthletes$, getEventLocations$).subscribe(([event, workoutsResponse, athletesResponse, eventLocationsResponse]) => {
                this.event = event;

                this.allWorkouts = workoutsResponse.body;
                this.event.tests = this.allWorkouts.filter(a => this.event.tests.some(wa => wa.id === a.id));
                this.suggestedWorkouts = this.allWorkouts.filter((a) => !this.event.tests.some((sa) => sa.id === a.id));

                this.allAthletes = athletesResponse.body;
                this.event.athletes = this.allAthletes.filter(a => this.event.athletes.some(wa => wa.id === a.id));
                this.suggestedAthletes = this.allAthletes.filter((a) => !this.event.athletes.some((sa) => sa.id === a.id));

                this.allEventLocations = eventLocationsResponse.body;

                this.date = this.event.date ? this.event.date.toDate() : null;
                this.setEventForm(this.event);
            });

        });
    }

    setEventForm(event: IEvent) {
        this.eventForm = this.formBuilder.group(event);
    }

    saveEvent(goBack: boolean = true) {
        if (this.eventForm.valid) {

            const eventToSave = this.eventForm.value as IEvent;
            eventToSave.date = moment(this.date);
            eventToSave.athletes = this.event.athletes;
            eventToSave.tests = this.event.tests;

            let saveEvent$;
            if (eventToSave.id) {
                saveEvent$ = this.eventService.update(eventToSave);
            } else {
                saveEvent$ = this.eventService.create(eventToSave);
            }


            saveEvent$.subscribe(
                (eventResponse: HttpResponse<IEvent>) => {
                    this.event = eventResponse.body;
                    this.setEventForm(this.event);
                    this.toastService.showSuccess('Událost uložena');

                    if (goBack) {
                        this.router.navigate(['/events/list']);
                    } else {
                        this.location.go(`/events/edit/${this.event.id}`);
                    }
                },
                (errorResponse: HttpErrorResponse) => {
                    this.toastService.showError('Událost nebyla uložena', errorResponse.error.detail);
                });
        }
    }
}
