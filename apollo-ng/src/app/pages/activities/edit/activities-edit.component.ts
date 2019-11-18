import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {Observable, zip} from 'rxjs';
import {map} from 'rxjs/operators';
import {MessageService, SelectItem} from 'primeng/api';
import {IActivity} from '../../../entities/model/activity.model';

@Component({
  selector: 'app-activity-edit',
  templateUrl: './activities-edit.component.html',
  styleUrls: ['./activities-edit.component.scss']
})
export class ActivitiesEditComponent implements OnInit {

  activityForm: FormGroup;
  activity: IActivity;
  activityId: number;

  units: Array<ActivityResultUnits>;
  unitsOptions: Array<any>;

  resultTypes: Array<ResultType>;
  resultTypesOptions: Array<any>;

  activityCategories: Array<IActivityCategory>;
  suggestedCategories: Array<IActivityCategory>;
  selectedCategories: Array<IActivityCategory>;

  controlsErrorsMaps: Map<string, Map<string, string>> = new Map();

  constructor(private activatedRoute: ActivatedRoute,
              private activityService: ActivityService,
              private activityCategoryService: ActivityCategoryService,
              private enumTranslateService: EnumTranslatorService,
              private messageService: MessageService,
              private router: Router) {
  }

  ngOnInit() {

    this.units = ActivityResultUnits.getAll();
    this.resultTypes = ResultType.getAll();

    this.unitsOptions = this.units.map(u => ({label: this.enumTranslateService.translate(u, 'plural-1p'), value: u.ordinal}));
    ArrayUtils.insertItem(this.unitsOptions, {label: 'NONE', value: null}, 0);

    this.resultTypesOptions = this.resultTypes.map(rt => ({label: this.enumTranslateService.translate(rt, 'plural'), value: rt.ordinal, selected: rt.ordinal === 0}));

    const params$ = this.activatedRoute.params;
    params$.subscribe((params) => {
      this.activityId = +params['id'];

      const getCategories$ = this.activityCategoryService.query();
      const getActivity$ = this.getActivity(this.activityId);

      zip(getActivity$, getCategories$).subscribe(([activity, categories]) => {
        this.activity = activity;
        this.activityCategories = categories.body;
        this.setActivityForm(this.activity, this.activityCategories);
      });

      // this.activityCategoryService.query().subscribe((activityCategoriesResponse: HttpResponse<Array<IActivityCategory>>) => {
      //   this.activityCategories = activityCategoriesResponse.body;
      // });

      // if (this.activityId) {
      //   this.activityService.find(this.activityId).subscribe((activityResponse: HttpResponse<IActivity>) => {
      //     this.activity = Activity.resolveResponse(activityResponse);
      //     this.setActivityForm(this.activity);
      //   });
      // } else {
      //   this.activity = new Activity();
      //   this.setActivityForm(this.activity);
      // }
    });
  }

  onItemSelect(item: any) {
    console.log(item);
  }

  onSelectAll(items: any) {
    console.log(items);
  }

  setActivityForm(activity: IActivity, categories: Array<IActivityCategory>) {

    this.activityForm = new FormGroup({
      id: new FormControl(activity.id),
      name: new FormControl(activity.name, Validators.required),
      description: new FormControl(activity.description),
      help: new FormControl(activity.help),
      key: new FormControl(activity.key),
      primaryResultValueUnit: new FormControl(activity.primaryResultValueUnit ? activity.primaryResultValueUnit.ordinal : null),
      secondaryResultValueUnit: new FormControl(activity.secondaryResultValueUnit ? activity.secondaryResultValueUnit.ordinal : null),
      minAge: new FormControl(activity.minAge, [CustomValidators.integerPositive, Validators.min(0), Validators.max(120)]),
      maxAge: new FormControl(activity.maxAge, [CustomValidators.integerPositive, Validators.min(0), Validators.max(120)]),
      primaryResultType: new FormControl(activity.primaryResultType ? activity.primaryResultType.ordinal : ResultType.LESS_IS_BETTER.ordinal),
      secondaryResultType: new FormControl(activity.secondaryResultType ? activity.secondaryResultType.ordinal : ResultType.LESS_IS_BETTER.ordinal),
    });

    if (activity.categories) {
      this.selectedCategories = categories.filter((c) => activity.categories.some((ac) => ac.id === c.id));
    }

    const nameErrorsMap = new Map();
    nameErrorsMap.set('required', 'Název je povinný!');
    this.controlsErrorsMaps.set('name', nameErrorsMap);

  }

  saveActivity() {
    if (this.activityForm.valid) {

      const activityToSave = <IActivity>this.activityForm.value;
      let saveActivity$;
      if (activityToSave.id) {
        saveActivity$ = this.activityService.update(activityToSave);
      } else {
        saveActivity$ = this.activityService.create(activityToSave);
      }

      activityToSave.categories = new Array<IActivityCategory>();
      activityToSave.categories = this.selectedCategories;

      saveActivity$.subscribe(
        (activityResponse: HttpResponse<IActivity>) => {
        this.activity = Activity.resolveResponse(activityResponse);
        this.setActivityForm(this.activity, this.activityCategories);
          this.messageService.add({severity: 'success', summary: 'Aktivita uložena'});
          this.router.navigate(['/admin/activities/list']);
      },
        (errorResponse: HttpErrorResponse) => {
          this.messageService.add({severity: 'error', summary: 'Aktivita nebyla uložena', detail: errorResponse.error.detail});
        });
    }
  }

  getActivity(activityId: number): Observable<IActivity> {
    if (activityId) {
      return this.activityService.find(this.activityId).pipe(map((activityResponse: HttpResponse<IActivity>) => {
        return Activity.resolveResponse(activityResponse);
      }));

    } else {
      return RxjsUtils.create(new Activity());
    }
  }

  selectedCategoriesChanged(selectedCategories: Array<any>) {
    console.log(selectedCategories);
  }
}

