export interface IAthleteActivityResultSplit {
  id?: number;
  value?: number;
  compareValue?: number;
  activityResultSplitId?: number;
  athleteActivityResultId?: number;
}

export class AthleteActivityResultSplit implements IAthleteActivityResultSplit {
  constructor(
    public id?: number,
    public value?: number,
    public compareValue?: number,
    public activityResultSplitId?: number,
    public athleteActivityResultId?: number
  ) {}
}
