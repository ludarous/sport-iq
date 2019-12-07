import { Component, Input, OnDestroy, OnInit, ViewEncapsulation } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Location} from '@angular/common';
import {IAthleteWorkout} from '../../../../entities/model/athlete-workout.model';
import {IWorkout} from '../../../../entities/model/workout.model';
import {AthleteService} from '../../../../services/rest/athlete.service';
import {ToastService} from '../../../../modules/core/services/message.service';
import {EnumTranslatorService} from '../../../../modules/shared-components/services/enum-translator.service';
import {AthleteWorkoutService} from '../../../../services/rest/athlete-workout.service';
import {IAthleteEvent} from '../../../../entities/model/athlete-event.model';
import {EventResultsService} from '../events-results.service';

@Component({
    selector: 'app-workout-general-result',
    templateUrl: './athlete-workout.component.html',
    styleUrls: ['./athlete-workout.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AthleteWorkoutComponent implements OnInit {

    constructor(private eventResultsService: EventResultsService,
                private athleteService: AthleteService,
                private athleteWorkoutService: AthleteWorkoutService,
                private activatedRoute: ActivatedRoute,
                private toastService: ToastService,
                private enumTranslateService: EnumTranslatorService,
                private formBuilder: FormBuilder,
                private router: Router,
                private location: Location) {
    }

    get workout(): IWorkout {
        return this.eventResultsService.selectedWorkout;
    }

    get athleteEvent(): IAthleteEvent {
        return this.eventResultsService.selectedAthleteEvent;
    }

    get athleteWorkout(): IAthleteWorkout {
        return this.eventResultsService.selectedAthleteWorkout;
    }

    athleteWorkoutForm: FormGroup;


    ngOnInit() {

        this.eventResultsService.selectedAthleteWorkoutChange$.subscribe((athleteWorkout: IAthleteWorkout) => {
            this.setAthleteWorkoutForm(this.athleteWorkout);
        });

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

            this.eventResultsService.saveAthleteWorkout(athleteWorkoutToSave).subscribe((athleteWorkout: IAthleteWorkout) => {
                this.setAthleteWorkoutForm(athleteWorkout);
            });
        }
    }

}
