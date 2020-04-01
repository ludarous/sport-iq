import { Subject } from 'rxjs';
import { Injectable } from "@angular/core";

@Injectable()
export class ActivitiesPagesService {
    public addActivityResultSource = new Subject();
    public addActivityResult$ = this.addActivityResultSource.asObservable();

    public activitySavedSource = new Subject();
    public activitySaved$ = this.activitySavedSource.asObservable();
}
