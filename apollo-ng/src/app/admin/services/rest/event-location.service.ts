import {Injectable, Injector} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {CrudBaseService} from '../../../modules/auth/services/rest/rest-base.service';
import {IUnit} from '../../entities/model/unit.model';

@Injectable()
export class EventLocationService extends CrudBaseService<IUnit> {
    constructor(injector: Injector) {
        super(environment.apiUrl, '/units', injector);
    }
}
