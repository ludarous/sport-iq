import {Injectable, Injector} from '@angular/core';
import {CrudBaseService} from '../../modules/auth/services/rest/rest-base.service';
import {environment} from '../../../environments/environment';
import {ActivityResult, IActivityResult} from '../../entities/model/activity-result.model';

@Injectable()
export class ActivityResultService extends CrudBaseService<IActivityResult> {
  constructor(injector: Injector) {
    super(environment.apiUrl, '/activity-results', injector, [ActivityResult.parseItemEnums]);
  }

}
