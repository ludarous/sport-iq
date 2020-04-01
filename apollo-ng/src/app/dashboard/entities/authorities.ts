import {AuthoritiesBase} from '../../modules/entities/enums/authorities-base';

export enum AuthoritiesEnum {
  ROLE_ADMIN,
  ROLE_USER,
  ROLE_SERVICEMAN,
  ROLE_MANAGER
}

export class Authorities extends AuthoritiesBase {

  constructor(value?: number | string, data?: any) {
    super(AuthoritiesEnum, value, data);
  }

  public static readonly ROLE_ADMIN = new Authorities(AuthoritiesEnum.ROLE_ADMIN);
  public static readonly ROLE_USER = new Authorities(AuthoritiesEnum.ROLE_USER);
  public static readonly ROLE_SERVICEMAN = new Authorities(AuthoritiesEnum.ROLE_SERVICEMAN);
  public static readonly ROLE_MANAGER = new Authorities(AuthoritiesEnum.ROLE_MANAGER);

}
