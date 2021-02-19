import { Moment } from 'moment';
import { IEventLocation } from 'app/shared/model/event-location.model';
import { IActivity } from 'app/shared/model/activity.model';
import { IUser } from 'app/core/user/user.model';

export interface IEvent {
  id?: number;
  name?: string;
  date?: Moment;
  eventLocation?: IEventLocation;
  activities?: IActivity[];
  entrants?: IUser[];
}

export class Event implements IEvent {
  constructor(
    public id?: number,
    public name?: string,
    public date?: Moment,
    public eventLocation?: IEventLocation,
    public activities?: IActivity[],
    public entrants?: IUser[]
  ) {}
}
