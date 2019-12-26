import {Injectable, Injector} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {CrudBaseService} from '../../modules/auth/services/rest/rest-base.service';
import {Event, IEvent} from '../../entities/model/event.model';
import {Observable} from 'rxjs';
import {HttpResponse} from '@angular/common/http';
import {AthleteEvent, IAthleteEvent} from '../../entities/model/athlete-event.model';
import {catchError, map} from 'rxjs/operators';
import {RxjsUtils} from '../../modules/core/utils/rxjs.utils';

@Injectable()
export class AthleteEventService extends CrudBaseService<IEvent> {
    constructor(injector: Injector) {
        super(environment.apiUrl, '/athlete-events', injector, [Event.parseItemEnums]);
    }

    getAthleteEventByEventIdAndAthleteId(eventId: number, athleteId: number): Observable<HttpResponse<IAthleteEvent>> {
        const params = {eventId, athleteId};
        return this.get(this.resourceUrl + `/by-event-id-and-athlete-id`, params);
    }

    getAthleteEventsByEventId(eventId: number): Observable<HttpResponse<Array<IAthleteEvent>>> {
        const params = {eventId};
        return this.get(this.resourceUrl + `/by-event-id`, params);
    }

    getAthleteEventsByAthleteId(athleteId: number): Observable<HttpResponse<Array<IAthleteEvent>>> {
        const params = {athleteId};
        return this.get(this.resourceUrl + `/by-athlete-id`, params);
    }

    getAthleteEventSummary(eventId: number, athleteId: number): Observable<HttpResponse<any>> {
        const params = {eventId, athleteId};
        return this.get(this.resourceUrl + `/summary`, params);
    }

    getAthleteEvent(eventId: number, athleteId: number): Observable<IAthleteEvent> {
        if (eventId && athleteId) {
            return this.getAthleteEventByEventIdAndAthleteId(eventId, athleteId)
                .pipe(map((eventResponse: HttpResponse<IAthleteEvent>) => {
                    return eventResponse.body;
                }), catchError((err, caught) => {
                    return RxjsUtils.create(new AthleteEvent());
                }));

        } else {
            return RxjsUtils.create(new AthleteEvent());
        }
    }

}
