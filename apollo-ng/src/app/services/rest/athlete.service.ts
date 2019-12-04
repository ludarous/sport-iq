import {Injectable, Injector} from '@angular/core';
import {environment} from '../../../environments/environment';
import {CrudBaseService} from '../../modules/auth/services/rest/rest-base.service';
import {Athlete, IAthlete} from '../../entities/model/athlete.model';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';
import {HttpResponse} from '@angular/common/http';
import {RxjsUtils} from '../../modules/core/utils/rxjs.utils';

@Injectable()
export class AthleteService extends CrudBaseService<IAthlete> {
    constructor(injector: Injector) {
        super(environment.apiUrl, '/athletes', injector, [Athlete.parseItemEnums]);
    }

    getAthlete(athleteId: number): Observable<IAthlete> {
        if (athleteId) {
            return this.find(athleteId)
                .pipe(map((eventResponse: HttpResponse<IAthlete>) => {
                    return eventResponse.body;
                }));

        } else {
            return RxjsUtils.create(new Athlete());
        }
    }
}
