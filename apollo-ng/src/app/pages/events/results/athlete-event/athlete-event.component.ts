import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {zip} from 'rxjs';
import {Location} from '@angular/common';
import {IAthleteEvent} from '../../../../entities/model/athlete-event.model';
import {IEvent} from '../../../../entities/model/event.model';
import {IAthlete} from '../../../../entities/model/athlete.model';
import {EventService} from '../../../../services/rest/event.service';
import {AthleteService} from '../../../../services/rest/athlete.service';
import {MessageService} from '../../../../modules/core/services/message.service';
import {EnumTranslatorService} from '../../../../modules/shared-components/services/enum-translator.service';
import {AthleteEventService} from '../../../../services/rest/athlete-event.service';

@Component({
    selector: 'app-event-general-result',
    templateUrl: './athlete-event.component.html',
    styleUrls: ['./athlete-event.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AthleteEventComponent implements OnInit {




    constructor(private eventService: EventService,
                private athleteService: AthleteService,
                private athleteEventService: AthleteEventService,
                private activatedRoute: ActivatedRoute,
                private messageService: MessageService,
                private enumTranslateService: EnumTranslatorService,
                private formBuilder: FormBuilder,
                private router: Router,
                private location: Location) {
    }

    private _event: IEvent;
    @Input()
    get event(): IEvent {
        return this._event;
    }

    set event(value: IEvent) {
        this._event = value;
    }


    private _athlete: IAthlete
    @Input()
    get athlete(): IAthlete {
        return this._athlete;
    }

    set athlete(value: IAthlete) {
        this._athlete = value;
    }


    private _athleteEvent: IAthleteEvent;
    @Input()
    get athleteEvent(): IAthleteEvent {
        return this._athleteEvent;
    }

    set athleteEvent(value: IAthleteEvent) {
        this._athleteEvent = value;
    }

    athleteEventForm: FormGroup;


    ngOnInit() {

        if (this.athleteEvent) {
            this.setAthleteEventForm(this.athleteEvent);
        }
    }

    setAthleteEventForm(athleteEvent: IAthleteEvent) {
        this.athleteEventForm = this.formBuilder.group(athleteEvent);
        this.athleteEventForm.get('eventId').setValidators(Validators.required);
        this.athleteEventForm.get('athleteId').setValidators(Validators.required);
    }

    saveAthleteEvent() {
        this.athleteEventForm.get('eventId').setValue(this.event.id);
        this.athleteEventForm.get('athleteId').setValue(this.athlete.id);

        if (this.athleteEventForm.valid) {

            const athleteEventToSave = this.athleteEventForm.value as IAthleteEvent;

            let saveAthleteEvent$;
            if (athleteEventToSave.id) {
                saveAthleteEvent$ = this.athleteEventService.update(athleteEventToSave);
            } else {
                saveAthleteEvent$ = this.athleteEventService.create(athleteEventToSave);
            }


            saveAthleteEvent$.subscribe(
                (athleteEventResponse: HttpResponse<IAthleteEvent>) => {
                    this._athleteEvent = athleteEventResponse.body;
                    this.setAthleteEventForm(this._athleteEvent);
                    this.messageService.showSuccess('Událost uložena');
                },
                (errorResponse: HttpErrorResponse) => {
                    this.messageService.showError('Událost nebyla uložena', errorResponse.error.detail);
                });
        }
    }

}
