import {Injectable, Injector} from '@angular/core';
import {CrudBaseService} from '../../modules/auth/services/rest/rest-base.service';
import {IActivity} from '../../entities/model/activity.model';
import {environment} from '../../../environments/environment';

@Injectable()
export class ActivityService extends CrudBaseService<IActivity> {
  constructor(injector: Injector) {
    super(environment.apiUrl, '/activities', injector);
  }

}
