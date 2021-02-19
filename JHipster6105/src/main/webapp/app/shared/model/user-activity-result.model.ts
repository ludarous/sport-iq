import { IUserActivityResultSplit } from 'app/shared/model/user-activity-result-split.model';
import { IActivityResult } from 'app/shared/model/activity-result.model';
import { IUserActivity } from 'app/shared/model/user-activity.model';

export interface IUserActivityResult {
  id?: number;
  value?: number;
  compareValue?: number;
  athleteActivityResultSplits?: IUserActivityResultSplit[];
  activityResult?: IActivityResult;
  userActivity?: IUserActivity;
}

export class UserActivityResult implements IUserActivityResult {
  constructor(
    public id?: number,
    public value?: number,
    public compareValue?: number,
    public athleteActivityResultSplits?: IUserActivityResultSplit[],
    public activityResult?: IActivityResult,
    public userActivity?: IUserActivity
  ) {}
}
