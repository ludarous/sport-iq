import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, zip} from 'rxjs';
import {map} from 'rxjs/operators';
import {TreeNode} from 'primeng/api';
import {RxjsUtils} from '../../../modules/core/utils/rxjs.utils';
import {MessageService} from '../../../modules/core/services/message.service';
import {WorkoutService} from '../../../services/rest/workout.service';
import {IWorkout, Workout} from '../../../entities/model/workout.model';
import {IActivity} from '../../../entities/model/activity.model';
import {ActivityService} from '../../../services/rest/activity.service';

@Component({
    selector: 'app-activity-categories-edit',
    templateUrl: './workouts-edit.component.html',
    styleUrls: ['./workouts-edit.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class WorkoutsEditComponent implements OnInit {

    constructor(private workoutService: WorkoutService,
                private activityService: ActivityService,
                private activatedRoute: ActivatedRoute,
                private messageService: MessageService,
                private formBuilder: FormBuilder,
                private router: Router) {
    }

    workoutId: number;
    workout: IWorkout;
    workoutForm: FormGroup;

    activities: Array<IActivity>;


    ngOnInit() {
        const params$ = this.activatedRoute.params;
        params$.subscribe((params) => {
            this.workoutId = +params.id;
            const getWorkout$ = this.getWorkout(this.workoutId);
            const getActivities$ = this.activityService.query({
                page: 0,
                size: 1000,
            });

            zip(getWorkout$, getActivities$).subscribe(([workout, activitiesResponse]) => {
                this.workout = workout;
                this.activities = activitiesResponse.body;
                // for (let activity of this.workout.activities) {
                //     activity = this.activities.find(a => a.id === activity.id);
                // }
                this.setWorkoutForm(this.workout);
            });

        });
    }

    setWorkoutForm(workout: IWorkout) {

        this.workoutForm = new FormGroup({
            id: new FormControl(workout.id),
            name: new FormControl(workout.name, [Validators.required]),
            description: new FormControl(workout.description, [Validators.required]),
        });
        this.workoutForm.setControl('activities', this.formBuilder.array(workout.activities));
    }

    saveWorkout() {
        if (this.workoutForm.valid) {

            const workoutToSave = this.workoutForm.value as IWorkout;
            workoutToSave.activities = this.workout.activities;

            let saveWorkout$;
            if (workoutToSave.id) {
                saveWorkout$ = this.workoutService.update(workoutToSave);
            } else {
                saveWorkout$ = this.workoutService.create(workoutToSave);
            }


            saveWorkout$.subscribe(
                (workoutResponse: HttpResponse<IWorkout>) => {
                    this.workout = workoutResponse.body;
                    this.setWorkoutForm(this.workout);
                    this.messageService.showSuccess('Test uložen');
                    this.router.navigate(['/workouts/list']);
                },
                (errorResponse: HttpErrorResponse) => {
                    this.messageService.showError('Test nebyl uložen', errorResponse.error.detail);
                });
        }
    }


    getWorkout(workoutId: number): Observable<IWorkout> {
        if (workoutId) {
            return this.workoutService
                .find(workoutId)
                .pipe(map((workoutResponse: HttpResponse<IWorkout>) => {
                    return workoutResponse.body;
                }));

        } else {
            return RxjsUtils.create(new Workout());
        }
    }

}
