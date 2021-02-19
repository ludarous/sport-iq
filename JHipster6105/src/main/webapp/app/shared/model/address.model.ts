import { ICountry } from 'app/shared/model/country.model';

export interface IAddress {
  id?: number;
  city?: string;
  street?: string;
  zipCode?: string;
  country?: ICountry;
}

export class Address implements IAddress {
  constructor(public id?: number, public city?: string, public street?: string, public zipCode?: string, public country?: ICountry) {}
}
