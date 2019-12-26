import {Injectable, Injector} from '@angular/core';
import {IUser, User} from '../../../entities/user';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {CrudBaseService} from './rest-base.service';
import {environment} from '../../../../../environments/environment';
import {Observable} from 'rxjs';


@Injectable()
export class UserService extends CrudBaseService<IUser> {
  constructor(injector: Injector) {
    super(environment.apiUrl, '/data-areas', injector, [User.parseEnums]);
  }

  createNewActivatedUser(user: IUser): Observable<HttpResponse<IUser>> {
    return this.post('/new-activated-user', user);
  }

  approveTerms(login: string): Observable<HttpResponse<any>> {
    return this.get(login + '/approve-terms');
  }

  approveProcessingOfPersonalDataContract(login: string): Observable<HttpResponse<any>> {
    return this.get(login + '/approve-processing-of-personal-data-contract');
  }
}
