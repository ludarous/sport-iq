export interface IAddress {
  id?: number;
  city?: string;
  street?: string;
  zipCode?: string;
  countryId?: number;
}

export class Address implements IAddress {
  constructor(public id?: number, public city?: string, public street?: string, public zipCode?: string, public countryId?: number) {}
}
