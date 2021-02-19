import { IActivityResultSplit } from 'app/shared/model/activity-result-split.model';
import { IUserActivityResult } from 'app/shared/model/user-activity-result.model';

export interface IUserActivityResultSplit {
  id?: number;
  value?: number;
  compareValue?: number;
  activityResultSplit?: IActivityResultSplit;
  userActivityResult?: IUserActivityResult;
}

export class UserActivityResultSplit implements IUserActivityResultSplit {
  constructor(
    public id?: number,
    public value?: number,
    public compareValue?: number,
    public activityResultSplit?: IActivityResultSplit,
    public userActivityResult?: IUserActivityResult
  ) {}
}
