import {Injectable, Injector} from '@angular/core';
import {CrudBaseService} from '../../modules/auth/services/rest/rest-base.service';
import {environment} from '../../../environments/environment';
import {ActivityResult, IActivityResult} from '../../entities/model/activity-result.model';
import {IActivityResultSplit} from '../../entities/model/activity-result-split.model';

@Injectable()
export class ActivityResultSplitService extends CrudBaseService<IActivityResultSplit> {
  constructor(injector: Injector) {
    super(environment.apiUrl, '/activity-result-splits', injector);
  }

}
