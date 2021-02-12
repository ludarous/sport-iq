import { Moment } from 'moment';
import { IActivity } from 'app/shared/model/activity.model';
import { IUser } from 'app/core/user/user.model';

export interface IEvent {
  id?: number;
  name?: string;
  date?: Moment;
  eventLocationName?: string;
  eventLocationId?: number;
  activities?: IActivity[];
  entrants?: IUser[];
}

export class Event implements IEvent {
  constructor(
    public id?: number,
    public name?: string,
    public date?: Moment,
    public eventLocationName?: string,
    public eventLocationId?: number,
    public activities?: IActivity[],
    public entrants?: IUser[]
  ) {}
}
