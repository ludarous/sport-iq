import {Injectable, Injector} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {CrudBaseService} from '../../modules/auth/services/rest/rest-base.service';
import {Event, IEvent} from '../../entities/model/event.model';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {HttpResponse} from '@angular/common/http';
import {RxjsUtils} from '../../modules/core/utils/rxjs.utils';

@Injectable()
export class EventService extends CrudBaseService<IEvent> {
    constructor(injector: Injector) {
        super(environment.apiUrl, '/events', injector, [Event.parseItemEnums]);
    }

    getEvent(eventId: number): Observable<IEvent> {
        if (eventId) {
            return this.find(eventId)
                .pipe(map((eventResponse: HttpResponse<IEvent>) => {
                    return eventResponse.body;
                }));

        } else {
            return RxjsUtils.create(new Event());
        }
    }
}
