import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, zip } from 'rxjs';
import { RxFormBuilder } from '@rxweb/reactive-form-validators';
import { Location } from '@angular/common';
import { AthleteService } from '../../../services/rest/athlete.service';
import { SportService } from '../../../services/rest/sport.service';
import { ISport } from '../../../entities/model/sport.model';
import { ToastService } from '../../../../modules/core/services/message.service';
import { IAthlete, Sex } from '../../../entities/model/athlete.model';
import { AuthService } from '../../../../modules/auth/services/auth.service';
import { IUser } from '../../../../modules/entities/user';
import { CalendarUtils } from '../../../../modules/core/utils/calendar-utils';
import {Moment} from 'moment';
import * as moment from 'moment';
import { SelectItem } from 'primeng/api';
import { EnumTranslatorService } from '../../../../modules/shared-components/services/enum-translator.service';
import { Laterality } from '../../../entities/model/enums/laterality.enum';
import { TranslateService } from '@ngx-translate/core';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

    csLocale = CalendarUtils.calendarLocale.cs;

    constructor(private athleteService: AthleteService,
                private authService: AuthService,
                private sportService: SportService,
                private activatedRoute: ActivatedRoute,
                private toastService: ToastService,
                private translateService: TranslateService,
                private enumTranslatorService: EnumTranslatorService,
                private formBuilder: FormBuilder,
                private rxFormBuilder: RxFormBuilder,
                private router: Router,
                private location: Location) {
    }
    sexTypes: Array<SelectItem>;
    lateralityTypes: Array<SelectItem>;

    currentUser: IUser;
    athlete: IAthlete;
    athleteForm: FormGroup;

    sports: Array<ISport>;
    selectedSports: Array<ISport>;

    termsAgreementText: string;
    photographyAgreementText: string;
    gdprAgreementText: string;
    medicalFitnessAgreementText: string;
    marketingAgreementText: string;

    get showLegalRepresentative(): boolean {
        if (this.athleteForm && this.athleteForm.get('birthDate') && this.athleteForm.get('birthDate').value) {
            const birthDate = moment(this.athleteForm.get('birthDate').value);
            const now = moment();
            const diff = now.diff(birthDate, 'years');
            return diff < 18;
        }
        return false;
    }
    ngOnInit() {
        this.load();
        this.termsAgreementText = this.translateService.instant('Souhlas s podmínkami služby SportIQ' );
        this.photographyAgreementText = this.translateService.instant('Souhlas s pořizováním obrazového materiálu' );
        this.gdprAgreementText = this.translateService.instant('Souhlas se zpracováním osobních údajů' );
        this.medicalFitnessAgreementText = this.translateService.instant('Prohlášení o zdravotní způsobilosti' );
        this.marketingAgreementText = this.translateService.instant('Souhlas se zasíláním marketingových materiálů' );

        this.sexTypes = Sex.getAll().map(s => {
            return {value: s, label: this.enumTranslatorService.translate(s)};
        });

        this.lateralityTypes = Laterality.getAll().map(s => {
            return {value: s, label: this.enumTranslatorService.translate(s)};
        });
    }

    load() {
        this.currentUser = this.authService.getCurrentUser();
        if (!this.currentUser) {
            console.warn('User not logged id!');
        }
        const getSports$ = this.sportService.query({
            page: 0,
            size: 1000,
        });

        getSports$.subscribe((sportsResponse) => {
            this.sports = sportsResponse.body;
            this.athlete = this.currentUser.athlete;
            this.setAthleteForm(this.athlete);
        });
    }

    setAthleteForm(athlete: IAthlete) {
        this.athleteForm = this.formBuilder.group(athlete);
        this.athleteForm.setControl('sports', this.formBuilder.array(athlete.sports));
        this.athleteForm.get('firstName').setValidators([Validators.required, Validators.minLength(3)]);
        this.athleteForm.get('lastName').setValidators([Validators.required, Validators.minLength(3)]);
        this.athleteForm.get('email').setValidators([Validators.required, Validators.email]);
    }

    saveAthlete$(): Observable<HttpResponse<IAthlete>> {
        if (this.athleteForm.valid) {

            const athleteToSave = this.athleteForm.value as IAthlete;
            athleteToSave.sports = this.athlete.sports;

            let saveAthlete$;
            if (athleteToSave.id) {
                saveAthlete$ = this.athleteService.update(athleteToSave);
            } else {
                saveAthlete$ = this.athleteService.create(athleteToSave);
            }
            return saveAthlete$;
        } else {
            throw new Error('Form is not valid');
        }
    }

    saveAthlete(goBack: boolean = true) {
        const saveAthlete$ = this.saveAthlete$();
        saveAthlete$.subscribe(
            (athleteResponse: HttpResponse<IAthlete>) => {
                this.athlete = athleteResponse.body;
                this.setAthleteForm(this.athlete);
                this.toastService.showSuccess('Profil uložen');
                this.authService.updateUser().subscribe();
                // if (goBack) {
                //     this.router.navigate(['/admin/activities/list']);
                // } else {
                //     this.location.go(`/admin/activities/edit/${this.athlete.id}`);
                // }
            },
            (errorResponse: HttpErrorResponse) => {
                this.toastService.showError('Profile nebyl uložen', errorResponse.error.detail);
            });
    }
}
