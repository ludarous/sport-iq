import {Component, Input, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Location} from '@angular/common';
import {IAthleteActivity} from '../../../../entities/model/athlete-activity.model';
import {IActivity} from '../../../../entities/model/activity.model';
import {AthleteService} from '../../../../services/rest/athlete.service';
import {ToastService} from '../../../../modules/core/services/message.service';
import {EnumTranslatorService} from '../../../../modules/shared-components/services/enum-translator.service';
import {IAthleteWorkout} from '../../../../entities/model/athlete-workout.model';
import {AthleteActivityService} from '../../../../services/rest/athlete-activity.service';
import {IActivityResult} from '../../../../entities/model/activity-result.model';
import {IActivityResultSplit} from '../../../../entities/model/activity-result-split.model';
import {IAthleteActivityResult} from '../../../../entities/model/athlete-activity-result.model';
import {IAthleteActivityResultSplit} from '../../../../entities/model/athlete-activity-result-split.model';

@Component({
    selector: 'app-activity-result-general-result',
    templateUrl: './athlete-activity-result.component.html',
    styleUrls: ['./athlete-activity-result.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class AthleteActivityResultComponent implements OnInit {

    constructor() {
    }

    @Input()
    athleteActivityResult: IAthleteActivityResult;

    @Input()
    activityResult: IActivityResult;

    private _showCompareValue: IActivityResult;
    @Input()
    get showCompareValue(): IActivityResult {
        return this._showCompareValue;
    }

    set showCompareValue(value: IActivityResult) {
        this._showCompareValue = value;
    }

    ngOnInit() {
        console.log(this.activityResult);
    }

    getActivityResultSplit(athleteActivityResultSplit: IAthleteActivityResultSplit): IActivityResultSplit {
        return this.activityResult.resultSplits.find(rs => rs.id === athleteActivityResultSplit.activityResultSplitId);
    }

    computedDifference(): number {
        return this.athleteActivityResult.value - this.athleteActivityResult.compareValue;
    }

}
