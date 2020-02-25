import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';
import { IAthleteWorkout, AthleteWorkout } from 'app/shared/model/athlete-workout.model';
import { AthleteWorkoutService } from './athlete-workout.service';
import { IWorkout } from 'app/shared/model/workout.model';
import { WorkoutService } from 'app/entities/workout/workout.service';
import { IAthleteEvent } from 'app/shared/model/athlete-event.model';
import { AthleteEventService } from 'app/entities/athlete-event/athlete-event.service';

@Component({
  selector: 'jhi-athlete-workout-update',
  templateUrl: './athlete-workout-update.component.html'
})
export class AthleteWorkoutUpdateComponent implements OnInit {
  isSaving: boolean;

  workouts: IWorkout[];

  athleteevents: IAthleteEvent[];

  editForm = this.fb.group({
    id: [],
    note: [],
    workoutId: [null, Validators.required],
    athleteEventId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected athleteWorkoutService: AthleteWorkoutService,
    protected workoutService: WorkoutService,
    protected athleteEventService: AthleteEventService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ athleteWorkout }) => {
      this.updateForm(athleteWorkout);
    });
    this.workoutService
      .query()
      .subscribe((res: HttpResponse<IWorkout[]>) => (this.workouts = res.body), (res: HttpErrorResponse) => this.onError(res.message));
    this.athleteEventService
      .query()
      .subscribe(
        (res: HttpResponse<IAthleteEvent[]>) => (this.athleteevents = res.body),
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(athleteWorkout: IAthleteWorkout) {
    this.editForm.patchValue({
      id: athleteWorkout.id,
      note: athleteWorkout.note,
      workoutId: athleteWorkout.workoutId,
      athleteEventId: athleteWorkout.athleteEventId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const athleteWorkout = this.createFromForm();
    if (athleteWorkout.id !== undefined) {
      this.subscribeToSaveResponse(this.athleteWorkoutService.update(athleteWorkout));
    } else {
      this.subscribeToSaveResponse(this.athleteWorkoutService.create(athleteWorkout));
    }
  }

  private createFromForm(): IAthleteWorkout {
    return {
      ...new AthleteWorkout(),
      id: this.editForm.get(['id']).value,
      note: this.editForm.get(['note']).value,
      workoutId: this.editForm.get(['workoutId']).value,
      athleteEventId: this.editForm.get(['athleteEventId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAthleteWorkout>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackWorkoutById(index: number, item: IWorkout) {
    return item.id;
  }

  trackAthleteEventById(index: number, item: IAthleteEvent) {
    return item.id;
  }
}
