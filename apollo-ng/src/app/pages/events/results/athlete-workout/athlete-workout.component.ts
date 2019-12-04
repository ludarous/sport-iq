import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Location} from '@angular/common';
import {IAthleteWorkout} from '../../../../entities/model/athlete-workout.model';
import {IWorkout} from '../../../../entities/model/workout.model';
import {AthleteService} from '../../../../services/rest/athlete.service';
import {MessageService} from '../../../../modules/core/services/message.service';
import {EnumTranslatorService} from '../../../../modules/shared-components/services/enum-translator.service';
import {AthleteWorkoutService} from '../../../../services/rest/athlete-workout.service';
import {IAthleteEvent} from '../../../../entities/model/athlete-event.model';

@Component({
    selector: 'app-workout-general-result',
    templateUrl: './athlete-workout.component.html',
    styleUrls: ['./athlete-workout.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AthleteWorkoutComponent implements OnInit {

    constructor(private athleteService: AthleteService,
                private athleteWorkoutService: AthleteWorkoutService,
                private activatedRoute: ActivatedRoute,
                private messageService: MessageService,
                private enumTranslateService: EnumTranslatorService,
                private formBuilder: FormBuilder,
                private router: Router,
                private location: Location) {
    }

    private _workout: IWorkout;
    @Input()
    get workout(): IWorkout {
        return this._workout;
    }

    set workout(value: IWorkout) {
        this._workout = value;
    }


    private _athleteEvent: IAthleteEvent
    @Input()
    get athleteEvent(): IAthleteEvent {
        return this._athleteEvent;
    }

    set athleteEvent(value: IAthleteEvent) {
        this._athleteEvent = value;
    }


    private _athleteWorkout: IAthleteWorkout;
    @Input()
    get athleteWorkout(): IAthleteWorkout {
        return this._athleteWorkout;
    }

    set athleteWorkout(value: IAthleteWorkout) {
        this._athleteWorkout = value;
    }

    athleteWorkoutForm: FormGroup;


    ngOnInit() {

        if (this.athleteWorkout) {
            this.setAthleteWorkoutForm(this.athleteWorkout);
        }

    }

    setAthleteWorkoutForm(athleteWorkout: IAthleteWorkout) {
        this.athleteWorkoutForm = this.formBuilder.group(athleteWorkout);
        this.athleteWorkoutForm.get('workoutId').setValidators(Validators.required);
        this.athleteWorkoutForm.get('athleteEventId').setValidators(Validators.required);
    }

    saveAthleteWorkout() {
        this.athleteWorkoutForm.get('workoutId').setValue(this.workout.id);
        this.athleteWorkoutForm.get('athleteEventId').setValue(this.athleteEvent.id);

        if (this.athleteWorkoutForm.valid) {

            const athleteWorkoutToSave = this.athleteWorkoutForm.value as IAthleteWorkout;

            let saveAthleteWorkout$;
            if (athleteWorkoutToSave.id) {
                saveAthleteWorkout$ = this.athleteWorkoutService.update(athleteWorkoutToSave);
            } else {
                saveAthleteWorkout$ = this.athleteWorkoutService.create(athleteWorkoutToSave);
            }


            saveAthleteWorkout$.subscribe(
                (athleteWorkoutResponse: HttpResponse<IAthleteWorkout>) => {
                    this._athleteWorkout = athleteWorkoutResponse.body;
                    this.setAthleteWorkoutForm(this._athleteWorkout);
                    this.messageService.showSuccess('Událost uložena');
                },
                (errorResponse: HttpErrorResponse) => {
                    this.messageService.showError('Událost nebyla uložena', errorResponse.error.detail);
                });
        }
    }

}
