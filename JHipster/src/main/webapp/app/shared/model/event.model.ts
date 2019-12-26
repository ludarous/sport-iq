import { Moment } from 'moment';
import { IAthleteEvent } from 'app/shared/model/athlete-event.model';
import { IWorkout } from 'app/shared/model/workout.model';
import { IAthlete } from 'app/shared/model/athlete.model';

export interface IEvent {
  id?: number;
  name?: string;
  date?: Moment;
  athleteEvents?: IAthleteEvent[];
  addressStreet?: string;
  addressId?: number;
  tests?: IWorkout[];
  athletes?: IAthlete[];
}

export class Event implements IEvent {
  constructor(
    public id?: number,
    public name?: string,
    public date?: Moment,
    public athleteEvents?: IAthleteEvent[],
    public addressStreet?: string,
    public addressId?: number,
    public tests?: IWorkout[],
    public athletes?: IAthlete[]
  ) {}
}
