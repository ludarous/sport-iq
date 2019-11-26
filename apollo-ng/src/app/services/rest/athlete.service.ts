import {Injectable, Injector} from '@angular/core';
import {environment} from '../../../environments/environment';
import {CrudBaseService} from '../../modules/auth/services/rest/rest-base.service';
import {Athlete, IAthlete} from '../../entities/model/athlete.model';

@Injectable()
export class AthleteService extends CrudBaseService<IAthlete> {
    constructor(injector: Injector) {
        super(environment.apiUrl, '/athletes', injector, [Athlete.parseItemEnums]);
    }
}
