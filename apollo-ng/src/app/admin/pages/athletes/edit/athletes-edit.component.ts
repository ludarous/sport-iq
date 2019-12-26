import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, zip} from 'rxjs';
import {map} from 'rxjs/operators';
import {RxjsUtils} from '../../../../modules/core/utils/rxjs.utils';
import {ToastService} from '../../../../modules/core/services/message.service';
import {AthleteService} from '../../../services/rest/athlete.service';
import {IAthlete, Athlete, Sex} from '../../../entities/model/athlete.model';
import {IActivity} from '../../../entities/model/activity.model';
import {ActivityService} from '../../../services/rest/activity.service';
import {CalendarUtils} from '../../../../modules/core/utils/calendar-utils';
import { Moment } from 'moment';
import * as moment from 'moment';
import {SelectItem} from 'primeng/api';
import {EnumTranslatorService} from '../../../../modules/shared-components/services/enum-translator.service';

@Component({
    selector: 'app-activity-categories-edit',
    templateUrl: './athletes-edit.component.html',
    styleUrls: ['./athletes-edit.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AthletesEditComponent implements OnInit {

    constructor(private athleteService: AthleteService,
                private activatedRoute: ActivatedRoute,
                private toastService: ToastService,
                private enumTranslateService: EnumTranslatorService,
                private formBuilder: FormBuilder,
                private router: Router) {
    }

    sexTypes: Array<SelectItem>;

    athleteId: number;
    athlete: IAthlete;
    athleteForm: FormGroup;

    csLocale = CalendarUtils.calendarLocale.cs;
    enLocale: any;

    birthDate: Date;

    ngOnInit() {

        this.sexTypes = Sex.getAll().map(s => {
            return {value: s, label: this.enumTranslateService.translate(s)};
        });

        const params$ = this.activatedRoute.params;
        params$.subscribe((params) => {
            this.athleteId = +params.id;
            const getAthlete$ = this.getAthlete(this.athleteId);

            getAthlete$.subscribe((athlete: IAthlete) => {
                this.athlete = athlete;
                this.birthDate = this.athlete.birthDate ? this.athlete.birthDate.toDate() : null;
                this.setAthleteForm(this.athlete);
            });

        });
    }

    setAthleteForm(athlete: IAthlete) {
        this.athleteForm = this.formBuilder.group(athlete);
    }

    saveAthlete() {
        if (this.athleteForm.valid) {

            const athleteToSave = this.athleteForm.value as IAthlete;
            athleteToSave.birthDate = moment(this.birthDate);

            let saveAthlete$;
            if (athleteToSave.id) {
                saveAthlete$ = this.athleteService.update(athleteToSave);
            } else {
                saveAthlete$ = this.athleteService.create(athleteToSave);
            }


            saveAthlete$.subscribe(
                (athleteResponse: HttpResponse<IAthlete>) => {
                    this.athlete = athleteResponse.body;
                    this.setAthleteForm(this.athlete);
                    this.toastService.showSuccess('Sportovec uložen');
                    this.router.navigate(['/athletes/list']);
                },
                (errorResponse: HttpErrorResponse) => {
                    this.toastService.showError('Sportovec nebyl uložen', errorResponse.error.detail);
                });
        }
    }


    getAthlete(athleteId: number): Observable<IAthlete> {
        if (athleteId) {
            return this.athleteService
                .find(athleteId)
                .pipe(map((athleteResponse: HttpResponse<IAthlete>) => {
                    return athleteResponse.body;
                }));

        } else {
            return RxjsUtils.create(new Athlete());
        }
    }

}
