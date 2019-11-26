import {Injectable, Injector} from '@angular/core';
import {environment} from '../../../environments/environment';
import {CrudBaseService} from '../../modules/auth/services/rest/rest-base.service';
import {Event, IEvent} from '../../entities/model/event.model';

@Injectable()
export class EventService extends CrudBaseService<IEvent> {
    constructor(injector: Injector) {
        super(environment.apiUrl, '/events', injector, [Event.parseItemEnums]);
    }
}
