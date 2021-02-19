import { Moment } from 'moment';
import { IUserActivity } from 'app/shared/model/user-activity.model';
import { IUser } from 'app/core/user/user.model';
import { IEvent } from 'app/shared/model/event.model';

export interface IUserEvent {
  id?: number;
  note?: string;
  registrationDate?: Moment;
  athleteActivities?: IUserActivity[];
  user?: IUser;
  event?: IEvent;
}

export class UserEvent implements IUserEvent {
  constructor(
    public id?: number,
    public note?: string,
    public registrationDate?: Moment,
    public athleteActivities?: IUserActivity[],
    public user?: IUser,
    public event?: IEvent
  ) {}
}
