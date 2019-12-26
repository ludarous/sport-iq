import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ToastService} from '../../../../modules/core/services/message.service';
import {ActivityService} from '../../../services/rest/activity.service';
import {IActivity} from '../../../entities/model/activity.model';

@Component({
    selector: 'app-activities-list',
    templateUrl: './activities-list.component.html',
    styleUrls: ['./activities-list.component.scss']
})
export class ActivitiesListComponent implements OnInit {

    tableCols: Array<any>;
    activities: Array<IActivity>;

    constructor(private activityService: ActivityService,
                private toastService: ToastService) {
    }

    ngOnInit() {

        this.tableCols = [
            {field: 'name', header: 'Název'},
            {field: 'description', header: 'Popis'},
        ];

        this.load();

    }

    load() {
        const getActivities$ = this.activityService.query({
            page: 0,
            size: 1000,
        });

        getActivities$.subscribe((activities: HttpResponse<Array<IActivity>>) => {
            this.activities = activities.body;
        });
    }

    delete(event, activity: IActivity) {
        event.stopPropagation();

        if (confirm('Opravdu chceš smazat aktivitu ' + activity.name)) {
            this.activityService.remove(activity.id).subscribe(() => {
                this.load();
            }, (errorResponse: HttpErrorResponse) => {
                this.toastService.showError('Aktivitu se nepodařilo smazat', errorResponse.message);
            });
        }

    }

}
