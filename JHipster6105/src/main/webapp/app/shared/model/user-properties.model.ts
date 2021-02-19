import { Moment } from 'moment';
import { Sex } from 'app/shared/model/enumerations/sex.model';

export interface IUserProperties {
  id?: number;
  birthDate?: Moment;
  phone?: string;
  nationality?: string;
  sex?: Sex;
}

export class UserProperties implements IUserProperties {
  constructor(public id?: number, public birthDate?: Moment, public phone?: string, public nationality?: string, public sex?: Sex) {}
}
