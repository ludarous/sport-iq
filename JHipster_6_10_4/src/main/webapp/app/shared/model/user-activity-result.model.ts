import { IUserActivityResultSplit } from 'app/shared/model/user-activity-result-split.model';

export interface IUserActivityResult {
  id?: number;
  value?: number;
  compareValue?: number;
  athleteActivityResultSplits?: IUserActivityResultSplit[];
  activityResultName?: string;
  activityResultId?: number;
  userActivityId?: number;
}

export class UserActivityResult implements IUserActivityResult {
  constructor(
    public id?: number,
    public value?: number,
    public compareValue?: number,
    public athleteActivityResultSplits?: IUserActivityResultSplit[],
    public activityResultName?: string,
    public activityResultId?: number,
    public userActivityId?: number
  ) {}
}
