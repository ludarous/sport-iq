import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAthleteWorkout, AthleteWorkout } from 'app/shared/model/athlete-workout.model';
import { AthleteWorkoutService } from './athlete-workout.service';
import { IWorkout } from 'app/shared/model/workout.model';
import { WorkoutService } from 'app/entities/workout/workout.service';
import { IAthleteEvent } from 'app/shared/model/athlete-event.model';
import { AthleteEventService } from 'app/entities/athlete-event/athlete-event.service';

type SelectableEntity = IWorkout | IAthleteEvent;

@Component({
  selector: 'jhi-athlete-workout-update',
  templateUrl: './athlete-workout-update.component.html'
})
export class AthleteWorkoutUpdateComponent implements OnInit {
  isSaving = false;
  workouts: IWorkout[] = [];
  athleteevents: IAthleteEvent[] = [];

  editForm = this.fb.group({
    id: [],
    note: [],
    workoutId: [null, Validators.required],
    athleteEventId: []
  });

  constructor(
    protected athleteWorkoutService: AthleteWorkoutService,
    protected workoutService: WorkoutService,
    protected athleteEventService: AthleteEventService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ athleteWorkout }) => {
      this.updateForm(athleteWorkout);

      this.workoutService.query().subscribe((res: HttpResponse<IWorkout[]>) => (this.workouts = res.body || []));

      this.athleteEventService.query().subscribe((res: HttpResponse<IAthleteEvent[]>) => (this.athleteevents = res.body || []));
    });
  }

  updateForm(athleteWorkout: IAthleteWorkout): void {
    this.editForm.patchValue({
      id: athleteWorkout.id,
      note: athleteWorkout.note,
      workoutId: athleteWorkout.workoutId,
      athleteEventId: athleteWorkout.athleteEventId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
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
      id: this.editForm.get(['id'])!.value,
      note: this.editForm.get(['note'])!.value,
      workoutId: this.editForm.get(['workoutId'])!.value,
      athleteEventId: this.editForm.get(['athleteEventId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAthleteWorkout>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
