export interface ILegalRepresentative {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phone?: string;
}

export class LegalRepresentative implements ILegalRepresentative {
  constructor(public id?: number, public firstName?: string, public lastName?: string, public email?: string, public phone?: string) {}
}
