export interface IUserActivityResultSplit {
  id?: number;
  value?: number;
  compareValue?: number;
  activityResultSplitId?: number;
  userActivityResultId?: number;
}

export class UserActivityResultSplit implements IUserActivityResultSplit {
  constructor(
    public id?: number,
    public value?: number,
    public compareValue?: number,
    public activityResultSplitId?: number,
    public userActivityResultId?: number
  ) {}
}
