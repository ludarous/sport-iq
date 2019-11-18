import {AuthoritiesBase} from './enums/authorities-base';

export interface DecodedToken {
  auth?: string;
  exp?: number;
  id?: number;
  sub?: string;
}

export interface IToken {
  token?: string;
  decodedToken?: DecodedToken;
  expirationDate?: Date;
  authorities: Array<AuthoritiesBase>;
}

export class Token implements IToken {
  token: string;
  decodedToken: DecodedToken;
  expirationDate: Date;
  authorities: Array<AuthoritiesBase>;
}
