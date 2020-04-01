import {Injectable, Injector} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {CrudBaseService} from '../../../modules/auth/services/rest/rest-base.service';
import {IUnit} from '../../entities/model/unit.model';
import { IEventLocation } from '../../entities/model/event-location.model';

@Injectable()
export class EventLocationService extends CrudBaseService<IEventLocation> {
    constructor(injector: Injector) {
        super(environment.apiUrl, '/event-locations', injector);
    }
}
