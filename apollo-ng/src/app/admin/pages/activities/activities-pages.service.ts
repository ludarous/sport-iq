import { Subject } from 'rxjs';

export class ActivitiesPagesService {
    public addActivityResultSource = new Subject();
    public addActivityResult$ = this.addActivityResultSource.asObservable();

    public activitySavedSource = new Subject();
    public activitySaved$ = this.activitySavedSource.asObservable();
}
