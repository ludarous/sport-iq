import { Component, OnInit } from '@angular/core';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {IActivity} from '../../../../entities/activity';
import {ActivityService} from '../../../../services/activity.service';
import {Router} from '@angular/router';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-activities-list',
  templateUrl: './activities-list.component.html',
  styleUrls: ['./activities-list.component.scss']
})
export class ActivitiesListComponent implements OnInit {

  tableCols: Array<any>;
  activities: Array<IActivity>;

  constructor(private activityService: ActivityService,
              private router: Router,
              private messageService: MessageService) { }

  ngOnInit() {

    this.tableCols = [
      { field: 'name', header: 'Název' },
      { field: 'description', header: 'Popis' },
      { field: 'help', header: 'Nápověda' },
    ];

    this.loadActivities();
  }

  loadActivities() {
    this.activityService.query({
      page: 0,
      size: 1000,
    }).subscribe((activities: HttpResponse<Array<IActivity>>) => {
      this.activities = activities.body;
    });
  }

  rowSelect(activity: IActivity) {
    this.router.navigate(['/admin/activities/edit', activity.id]);
  }

  delete(event, activity: IActivity) {
    event.stopPropagation();

    if (confirm('Opravdu chceš smazat aktivitu ' + activity.name)) {
      this.activityService.delete(activity.id).subscribe(() => {
        this.loadActivities();
      }, (errorResponse: HttpErrorResponse) => {
        this.messageService.add({severity: 'error', summary: 'Aktivitu se nepodařilo smazat', detail: errorResponse.error.detail});
      });
    }
  }

  create() {
    this.router.navigate(['/pages/admin/activities/create']);
  }

  edit(activity: IActivity) {
    this.router.navigate(['/pages/admin/activities/edit', activity.id]);
  }
}
