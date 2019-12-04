import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {IActivity} from '../../../../entities/model/activity.model';
import {IActivityResult, ResultType} from '../../../../entities/model/activity-result.model';
import {IUnit, Unit} from '../../../../entities/model/unit.model';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ActivityResultService} from '../../../../services/rest/activity-result.service';
import {ToastService} from '../../../../modules/core/services/message.service';
import {DynamicDialogConfig, DynamicDialogRef, SelectItem} from 'primeng/api';
import {PrimengUtils} from '../../../../modules/core/utils/primeng.utils';
import {EnumTranslatorService} from '../../../../modules/shared-components/services/enum-translator.service';
import {ActivityResultSplit, IActivityResultSplit} from '../../../../entities/model/activity-result-split.model';
import {ArrayUtils} from '../../../../modules/core/utils/array.utils';
import {ActivityResultSplitService} from '../../../../services/rest/activity-result-split.service';

@Component({
    selector: 'app-activity-results-edit',
    templateUrl: './activity-results-edit.component.html',
    styleUrls: ['./activity-results-edit.component.scss']
})
export class ActivityResultsEditComponent implements OnInit {


    constructor(private activityResultService: ActivityResultService,
                private activityResultSplitService: ActivityResultSplitService,
                private ToastService: ToastService,
                private formBuilder: FormBuilder,
                private enumTranslateService: EnumTranslatorService,
                public ref: DynamicDialogRef,
                public config: DynamicDialogConfig) {
    }


    activityResult: IActivityResult;
    units: Array<IUnit>;

    resultTypes: Array<SelectItem>;

    activityResultForm: FormGroup;
    useSplits: boolean;

    ngOnInit() {
        this.activityResult = this.config.data.result;
        this.units = this.config.data.units;
        this.useSplits = (this.activityResult.resultSplits.length > 0)  && !!this.activityResult.id;

        this.setActivityResultForm(this.activityResult);
        this.resultTypes = ResultType.getAll().map(v => {
            return {value: v, label: this.enumTranslateService.translate(v)};
        });
    }

    setActivityResultForm(activityResult: IActivityResult) {

        this.activityResultForm = new FormGroup({
            id: new FormControl(activityResult.id),
            name: new FormControl(activityResult.name, [Validators.required]),
            activityId: new FormControl(activityResult.activityId),
            resultType: new FormControl(activityResult.resultType ?
                activityResult.resultType :
                ResultType.LESS_IS_BETTER, [Validators.required]),
            resultUnit: this.formBuilder.group(activityResult.resultUnit ? activityResult.resultUnit : this.units[0]),
        });
    }

    saveActivityResult(close = true) {
        if (this.activityResultForm.valid) {

            const activityResultToSave = this.activityResultForm.value as IActivityResult;
            activityResultToSave.resultSplits = this.activityResult.resultSplits;

            let saveActivityResult$;
            if (activityResultToSave.id) {
                saveActivityResult$ = this.activityResultService.update(activityResultToSave);
            } else {
                saveActivityResult$ = this.activityResultService.create(activityResultToSave);
            }


            saveActivityResult$.subscribe(
                (activityResultResponse: HttpResponse<IActivityResult>) => {
                    this.activityResult = activityResultResponse.body;
                    this.ToastService.showSuccess('Jednotka uložena');
                    if (close) {
                        this.ref.close(activityResultResponse.body);
                    }
                },
                (errorResponse: HttpErrorResponse) => {
                    this.ToastService.showError('Jednota nebyla uložena', errorResponse.error.detail);
                });
        }
    }

    addSplitResult() {
        const split = new ActivityResultSplit(this.activityResult.id, this.units[0]);
        this.activityResult.resultSplits.push(split);
    }

    deleteSplit($event: MouseEvent, item: IActivityResultSplit) {
        if (item.id) {
            this.activityResultSplitService.remove(item.id).subscribe(() => {
                ArrayUtils.removeItem(this.activityResult.resultSplits, item);
            });
        } else {
            ArrayUtils.removeItem(this.activityResult.resultSplits, item);
        }
    }
}
