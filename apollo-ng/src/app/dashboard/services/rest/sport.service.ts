import {Injectable, Injector} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {CrudBaseService} from '../../../modules/auth/services/rest/rest-base.service';
import {IUnit} from '../../entities/model/unit.model';
import { ISport } from '../../entities/model/sport.model';

@Injectable()
export class SportService extends CrudBaseService<ISport> {
    constructor(injector: Injector) {
        super(environment.apiUrl, '/sports', injector);
    }
}
