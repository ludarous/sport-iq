import {Injectable, Injector} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {CrudBaseService} from '../../modules/auth/services/rest/rest-base.service';
import {IActivityCategory} from '../../entities/model/activity-category.model';

@Injectable()
export class ActivityCategoryService extends CrudBaseService<IActivityCategory> {
    constructor(injector: Injector) {
        super(environment.apiUrl, '/activity-categories', injector);
    }
}
