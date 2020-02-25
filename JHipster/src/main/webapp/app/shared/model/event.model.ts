import { Moment } from 'moment';
import { IAthleteEvent } from 'app/shared/model/athlete-event.model';
import { IWorkout } from 'app/shared/model/workout.model';
import { IAthlete } from 'app/shared/model/athlete.model';

export interface IEvent {
  id?: number;
  name?: string;
  date?: Moment;
  athleteEvents?: IAthleteEvent[];
  eventLocationName?: string;
  eventLocationId?: number;
  tests?: IWorkout[];
  athletes?: IAthlete[];
}

export class Event implements IEvent {
  constructor(
    public id?: number,
    public name?: string,
    public date?: Moment,
    public athleteEvents?: IAthleteEvent[],
    public eventLocationName?: string,
    public eventLocationId?: number,
    public tests?: IWorkout[],
    public athletes?: IAthlete[]
  ) {}
}
