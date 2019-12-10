import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {ToastService} from '../../../modules/core/services/message.service';
import {AthleteService} from '../../../services/rest/athlete.service';
import {Athlete, IAthlete, Sex} from '../../../entities/model/athlete.model';
import {CalendarUtils} from '../../../modules/core/utils/calendar-utils';
import {EnumTranslatorService} from '../../../modules/shared-components/services/enum-translator.service';
import {Moment} from 'moment';
import * as moment from 'moment';
import {MenuItem} from 'primeng/api';

@Component({
    selector: 'app-activity-categories-edit',
    templateUrl: './athletes-card.component.html',
    styleUrls: ['./athletes-card.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AthletesCardComponent implements OnInit {

    constructor(private athleteService: AthleteService,
                private activatedRoute: ActivatedRoute,
                private toastService: ToastService,
                private enumTranslateService: EnumTranslatorService,
                private formBuilder: FormBuilder,
                private router: Router) {
    }

    athleteId: number;
    athlete: IAthlete = new Athlete();

    cardMenuItems: Array<MenuItem>;

    csLocale = CalendarUtils.calendarLocale.cs;
    enLocale: any;

    ngOnInit() {

        this.cardMenuItems = [
            {
                label: 'Výsledky událostí',

            }, {
               label: 'Statistiky',
            }
        ];

        this.athlete.firstName = 'Luděk';
        this.athlete.lastName = 'Rous';
        this.athlete.birthDate = moment('01.11.1985', 'MM.DD.YYYY');
        this.athlete.sex = Sex.MALE;
        this.athlete.email = 'rousludek@gmail.com';
        this.athlete.nationality = 'CZ';
        // const params$ = this.activatedRoute.params;
        // params$.subscribe((params) => {
        //     this.athleteId = +params.id;
        //     const getAthlete$ = this.athleteService.getAthlete(this.athleteId);
        //
        //     getAthlete$.subscribe((athlete: IAthlete) => {
        //         this.athlete = athlete;
        //     });
        //
        // });
    }

}
