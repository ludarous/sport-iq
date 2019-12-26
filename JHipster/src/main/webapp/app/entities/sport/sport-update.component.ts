import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ISport, Sport } from 'app/shared/model/sport.model';
import { SportService } from './sport.service';

@Component({
  selector: 'jhi-sport-update',
  templateUrl: './sport-update.component.html'
})
export class SportUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]]
  });

  constructor(protected sportService: SportService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ sport }) => {
      this.updateForm(sport);
    });
  }

  updateForm(sport: ISport) {
    this.editForm.patchValue({
      id: sport.id,
      name: sport.name
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const sport = this.createFromForm();
    if (sport.id !== undefined) {
      this.subscribeToSaveResponse(this.sportService.update(sport));
    } else {
      this.subscribeToSaveResponse(this.sportService.create(sport));
    }
  }

  private createFromForm(): ISport {
    return {
      ...new Sport(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISport>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
