import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable, zip } from 'rxjs';
import { map } from 'rxjs/operators';
import { RxjsUtils } from '../../../../modules/core/utils/rxjs.utils';
import { ToastService } from '../../../../modules/core/services/message.service';
import { ActivityService } from '../../../services/rest/activity.service';
import { Activity, IActivity } from '../../../entities/model/activity.model';
import { ActivityCategoryService } from '../../../services/rest/activity-category.service';
import { UnitService } from '../../../services/rest/unit.service';
import { IActivityCategory } from '../../../entities/model/activity-category.model';
import { IUnit, Unit } from '../../../entities/model/unit.model';
import { ArrayUtils } from '../../../../modules/core/utils/array.utils';
import { SelectItem } from 'primeng/api';
import { MultiSelectItem } from 'primeng/primeng';
import { PrimengUtils } from '../../../../modules/core/utils/primeng.utils';
import { RxFormBuilder } from '@rxweb/reactive-form-validators';
import { CustomValidators } from '../../../../modules/shared-components/validators/custom-validators';
import { IActivityResult } from '../../../entities/model/activity-result.model';
import { ActivitiesPagesService } from '../activities-pages.service';
import { Location } from '@angular/common';

@Component({
    selector: 'app-activity-categories-edit',
    templateUrl: './activities-edit.component.html',
    styleUrls: ['./activities-edit.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class ActivitiesEditComponent implements OnInit {

    constructor(private activityService: ActivityService,
                private activityCategoryService: ActivityCategoryService,
                private activitiesPagesService: ActivitiesPagesService,
                private unitService: UnitService,
                private activatedRoute: ActivatedRoute,
                private toastService: ToastService,
                private formBuilder: FormBuilder,
                private rxFormBuilder: RxFormBuilder,
                private router: Router,
                private location: Location) {
    }

    activityId: number;
    activity: IActivity;
    activityForm: FormGroup;

    categories: Array<IActivityCategory>;
    units: Array<IUnit>;
    unitsSelectItems: Array<SelectItem>;

    selectedTargetValueUnit: IUnit;
    selectedActivityCategories: Array<IActivityCategory>;

    ngOnInit() {
        const params$ = this.activatedRoute.params;
        params$.subscribe((params) => {
            this.activityId = +params.id;
            this.load();
        });
    }

    load() {
        const getActivity$ = this.getActivity(this.activityId);
        const getCategories$ = this.activityCategoryService.query({
            page: 0,
            size: 1000,
        });
        const getUnits$ = this.unitService.query({
            page: 0,
            size: 1000,
        });

        zip(getActivity$, getCategories$, getUnits$).subscribe(([activity, categoriesResponse, unitsResponse]) => {
            this.activity = activity;
            this.categories = categoriesResponse.body;
            this.units = unitsResponse.body;
            this.unitsSelectItems = unitsResponse.body.map(v => PrimengUtils.getSelectItem(v, 'id', 'name'));
            this.setActivityForm(this.activity);
        });
    }

    setActivityForm(activity: IActivity) {
        this.activityForm = this.formBuilder.group(activity);
        this.activityForm.setControl('categories', this.formBuilder.array(activity.categories));
        this.activityForm.get('name').setValidators([Validators.required, Validators.minLength(3)]);
        this.activityForm.get('minAge').setValidators([CustomValidators.integerPositive]);
        this.activityForm.get('maxAge').setValidators([CustomValidators.integerPositive]);
    }

    saveActivity$() {
        if (this.activityForm.valid) {

            const activityToSave = this.activityForm.value as IActivity;
            activityToSave.categories = this.activity.categories;

            let saveActivity$;
            if (activityToSave.id) {
                saveActivity$ = this.activityService.update(activityToSave);
            } else {
                saveActivity$ = this.activityService.create(activityToSave);
            }
            return saveActivity$;
        } else {
            throw new Error('Form is not valid');
        }
    }

    saveActivity(goBack: boolean = true) {
        const saveActivity$ = this.saveActivity$();
        saveActivity$.subscribe(
            (activityResponse: HttpResponse<IActivity>) => {
                this.activity = activityResponse.body;
                this.activityId = this.activity.id;
                this.setActivityForm(this.activity);
                this.toastService.showSuccess('Aktivita uložena');
                if (goBack) {
                    this.router.navigate(['/activities/list']);
                } else {
                    this.location.go(`/activities/edit/${this.activity.id}`);
                }
            },
            (errorResponse: HttpErrorResponse) => {
                this.toastService.showError('Aktivita nebyla uložena', errorResponse.error.detail);
            });
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

    onResultDelete($event: IActivityResult) {
        this.load();
    }

    onResultSaved($event: IActivityResult) {
        this.load();
    }
}
