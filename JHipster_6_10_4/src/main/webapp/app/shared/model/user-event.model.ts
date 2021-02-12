import { Moment } from 'moment';
import { IUserActivity } from 'app/shared/model/user-activity.model';

export interface IUserEvent {
  id?: number;
  note?: string;
  registrationDate?: Moment;
  athleteActivities?: IUserActivity[];
  userId?: number;
  eventId?: number;
}

export class UserEvent implements IUserEvent {
  constructor(
    public id?: number,
    public note?: string,
    public registrationDate?: Moment,
    public athleteActivities?: IUserActivity[],
    public userId?: number,
    public eventId?: number
  ) {}
}
