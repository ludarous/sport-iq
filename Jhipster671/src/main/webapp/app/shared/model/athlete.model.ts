import { Moment } from 'moment';
import { IEvent } from 'app/shared/model/event.model';
import { Sex } from 'app/shared/model/enumerations/sex.model';

export interface IAthlete {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  birthDate?: Moment;
  nationality?: string;
  sex?: Sex;
  addressId?: number;
  userId?: number;
  events?: IEvent[];
}

export class Athlete implements IAthlete {
  constructor(
    public id?: number,
    public firstName?: string,
    public lastName?: string,
    public email?: string,
    public birthDate?: Moment,
    public nationality?: string,
    public sex?: Sex,
    public addressId?: number,
    public userId?: number,
    public events?: IEvent[]
  ) {}
}