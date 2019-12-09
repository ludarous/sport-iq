import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {Location} from '@angular/common';
import {IAthleteEvent} from '../../../../entities/model/athlete-event.model';
import {IEvent} from '../../../../entities/model/event.model';
import {IAthlete} from '../../../../entities/model/athlete.model';
import {EventService} from '../../../../services/rest/event.service';
import {AthleteService} from '../../../../services/rest/athlete.service';
import {EnumTranslatorService} from '../../../../modules/shared-components/services/enum-translator.service';
import {AthleteEventService} from '../../../../services/rest/athlete-event.service';
import {EventResultsService} from '../events-results.service';
import {IAthleteWorkout} from '../../../../entities/model/athlete-workout.model';

@Component({
    selector: 'app-event-general-result',
    templateUrl: './athlete-event.component.html',
    styleUrls: ['./athlete-event.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AthleteEventComponent implements OnInit {

    constructor(private eventResultsService: EventResultsService,
                private eventService: EventService,
                private athleteService: AthleteService,
                private athleteEventService: AthleteEventService,
                private activatedRoute: ActivatedRoute,
                private enumTranslateService: EnumTranslatorService,
                private formBuilder: FormBuilder,
                private router: Router,
                private location: Location) {
    }

    @Input()
    event: IEvent;

    @Input()
    athlete: IAthlete;

    @Input()
    athleteEvent: IAthleteEvent;

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
        if (this.athleteEventForm.valid) {

            const athleteEventToSave = this.athleteEventForm.value as IAthleteEvent;

            this.eventResultsService.saveAthleteEvent(athleteEventToSave).subscribe((athleteEvent: IAthleteEvent) => {
                this.setAthleteEventForm(athleteEvent);
            });
        }
    }

}
