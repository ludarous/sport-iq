import {Injectable, Injector} from '@angular/core';
import {environment} from '../../../environments/environment';
import {CrudBaseService} from '../../modules/auth/services/rest/rest-base.service';
import {Event, IEvent} from '../../entities/model/event.model';
import {Observable} from 'rxjs';
import {HttpResponse} from '@angular/common/http';
import {IAthleteEvent} from '../../entities/model/athlete-event.model';

@Injectable()
export class AthleteEventService extends CrudBaseService<IEvent> {
    constructor(injector: Injector) {
        super(environment.apiUrl, '/athlete-events', injector, [Event.parseItemEnums]);
    }

    getAthleteEventByEventIdAndAthleteId(eventId: number, athleteId: number): Observable<HttpResponse<IAthleteEvent>> {
        const params = {eventId, athleteId};
        return this.get(this.resourceUrl + `/by-event-id-and-athlete-id`, params);
    }
}
