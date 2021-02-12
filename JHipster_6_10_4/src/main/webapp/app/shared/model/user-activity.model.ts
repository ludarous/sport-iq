import { Moment } from 'moment';
import { IUserActivityResult } from 'app/shared/model/user-activity-result.model';

export interface IUserActivity {
  id?: number;
  note?: string;
  date?: Moment;
  athleteActivityResults?: IUserActivityResult[];
  activityName?: string;
  activityId?: number;
  userEventId?: number;
}

export class UserActivity implements IUserActivity {
  constructor(
    public id?: number,
    public note?: string,
    public date?: Moment,
    public athleteActivityResults?: IUserActivityResult[],
    public activityName?: string,
    public activityId?: number,
    public userEventId?: number
  ) {}
}
