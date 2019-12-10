import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {IActivity} from '../../../../entities/model/activity.model';
import {HttpErrorResponse} from '@angular/common/http';
import {ActivityResultService} from '../../../../services/rest/activity-result.service';
import {ActivityResult, IActivityResult} from '../../../../entities/model/activity-result.model';
import {ToastService} from '../../../../modules/core/services/message.service';
import {DialogService, SelectItem} from 'primeng/api';
import {EnumTranslatorService} from '../../../../modules/shared-components/services/enum-translator.service';
import {IUnit} from '../../../../entities/model/unit.model';
import {Dialog} from 'primeng/dialog';
import {ActivityResultsEditComponent} from '../edit/activity-results-edit.component';
import { ActivitiesPagesService } from '../../activities-pages.service';

@Component({
    selector: 'app-activity-results-list',
    templateUrl: './activity-results-list.component.html',
    styleUrls: ['./activity-results-list.component.scss']
})
export class ActivityResultsListComponent implements OnInit {

    constructor(private activityResultService: ActivityResultService,
                private toastService: ToastService,
                private enumTranslateService: EnumTranslatorService,
                private activitiesPagesService: ActivitiesPagesService,
                public dialogService: DialogService) {
    }

    tableCols: Array<any>;

    @ViewChild('editedResultDialog', {static: true})
    dialog: Dialog;

    @Input()
    activity: IActivity;

    @Input()
    units: Array<IUnit>;

    @Output()
    resultDelete: EventEmitter<IActivityResult> = new EventEmitter<IActivityResult>();

    @Output()
    activityResultSaved: EventEmitter<IActivityResult> = new EventEmitter<IActivityResult>();

    ngOnInit() {
        this.tableCols = [
            {field: 'name', header: 'Název'},
            {field: 'unit', header: 'Jednotka', map: (v: IActivityResult, c) => v.resultUnit.name},
            {field: 'type', header: 'Typ výsledku', map: (v: IActivityResult, c) =>  this.enumTranslateService.translate(v.resultType)},
            {header: 'Mezivýsledky', map: (v: IActivityResult, c) =>  v.resultSplits ? v.resultSplits.length : ''},
        ];
    }

    delete(event, activityResult: IActivityResult) {
        event.stopPropagation();

        if (confirm('Opravdu chceš smazat výsledek aktivity ' + activityResult.name)) {
            this.activityResultService.remove(activityResult.id).subscribe(() => {
                this.resultDelete.emit(activityResult);
            }, (errorResponse: HttpErrorResponse) => {
                this.toastService.showError('Aktivitu se nepodařilo smazat', errorResponse.message);
            });
        }

    }

    editActivityResult(item?: IActivityResult) {

        if (!item) {
            const activityResult = new ActivityResult();
            activityResult.activityId = this.activity.id;
            item = activityResult;
        }

        const ref = this.dialogService.open(ActivityResultsEditComponent, {
            width: '70%',
            data: {result: item, units: this.units}
        });

        ref.onClose.subscribe((activityResult: IActivityResult) => {
            this.activityResultSaved.emit(activityResult);
        });

    }
}
