import {Injectable} from '@angular/core';

@Injectable()
export class ModuleConfig {
  environment: any;
  tokenStorageKey? = 'token';
  userStorageKey? = 'user';
  tokenExpirationTimeReserve? = 60; // Token expiration time reserve in seconds
  appAuthoritiesEnum?;
}
