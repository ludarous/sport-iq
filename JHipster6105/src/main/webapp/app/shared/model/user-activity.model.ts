import { Moment } from 'moment';
import { IUserActivityResult } from 'app/shared/model/user-activity-result.model';
import { IActivity } from 'app/shared/model/activity.model';
import { IUserEvent } from 'app/shared/model/user-event.model';

export interface IUserActivity {
  id?: number;
  note?: string;
  date?: Moment;
  athleteActivityResults?: IUserActivityResult[];
  activity?: IActivity;
  userEvent?: IUserEvent;
}

export class UserActivity implements IUserActivity {
  constructor(
    public id?: number,
    public note?: string,
    public date?: Moment,
    public athleteActivityResults?: IUserActivityResult[],
    public activity?: IActivity,
    public userEvent?: IUserEvent
  ) {}
}
