import {AuthoritiesBase} from './enums/authorities-base';

export interface IUser {
  id: string;
  login?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  authorities?: Array<AuthoritiesBase>;

  activated?: boolean;
  createdDate?: string;

  langKey?: string;
}


export class User implements IUser {
  id: string;
  login: string;
  firstName: string;
  lastName: string;
  email: string;
  authorities?: Array<AuthoritiesBase>;

  activated: boolean;
  createdDate: string;

  langKey: string;

  static parseEnums(item: IUser, appAuthoritiesEnum: any): IUser {
    if (item) {
      const authorities = new Array<AuthoritiesBase>();
      if (item.authorities) {
        for (const authority of item.authorities) {
          authorities.push(new AuthoritiesBase(appAuthoritiesEnum, <any>authority));
        }
      }
      item.authorities = authorities;
    }
    return item;
  }
}
