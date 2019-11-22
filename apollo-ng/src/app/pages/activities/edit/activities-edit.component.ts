import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, zip} from 'rxjs';
import {map} from 'rxjs/operators';
import {TreeNode} from 'primeng/api';
import {RxjsUtils} from '../../../modules/core/utils/rxjs.utils';
import {MessageService} from '../../../modules/core/services/message.service';
import {ActivityService} from '../../../services/rest/activity.service';
import {IActivity, Activity} from '../../../entities/model/activity.model';
import {RxFormBuilder} from '@rxweb/reactive-form-validators';
import {FormUtils} from '../../../modules/core/utils/form.utils';

@Component({
    selector: 'app-activity-categories-edit',
    templateUrl: './activities-edit.component.html',
    styleUrls: ['./activities-edit.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ActivitiesEditComponent implements OnInit {

    constructor(private activityService: ActivityService,
                private activatedRoute: ActivatedRoute,
                private messageService: MessageService,
                private formBuilder: FormBuilder,
                private router: Router) {
    }

    activityId: number;
    activity: IActivity;
    activityForm: FormGroup;


    ngOnInit() {
        const params$ = this.activatedRoute.params;
        params$.subscribe((params) => {
            this.activityId = +params.id;
            const getActivity$ = this.getActivity(this.activityId);
            const getCategories$ = this.activityService.query({
                page: 0,
                size: 1000,
            });

            getActivity$.subscribe((activity: IActivity) => {
                this.activity = activity;
                this.setActivityForm(this.activity);
            });

        });
    }

    setActivityForm(activity: IActivity) {
        this.activityForm = this.formBuilder.group(activity);
    }

    saveActivity() {
        if (this.activityForm.valid) {

            const activityToSave = this.activityForm.value as IActivity;
            let saveActivity$;
            if (activityToSave.id) {
                saveActivity$ = this.activityService.update(activityToSave);
            } else {
                saveActivity$ = this.activityService.create(activityToSave);
            }


            saveActivity$.subscribe(
                (activityResponse: HttpResponse<IActivity>) => {
                    this.activity = activityResponse.body;
                    this.setActivityForm(this.activity);
                    this.messageService.showSuccess('Jednotka uložena');
                    this.router.navigate(['/activities/list']);
                },
                (errorResponse: HttpErrorResponse) => {
                    this.messageService.showError('Jednota nebyla uložena', errorResponse.error.detail);
                });
        }
    }


    getActivity(activityId: number): Observable<IActivity> {
        if (activityId) {
            return this.activityService
                .find(activityId)
                .pipe(map((activityResponse: HttpResponse<IActivity>) => {
                return activityResponse.body;
            }));

        } else {
            return RxjsUtils.create(new Activity());
        }
    }

}
