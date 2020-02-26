import { IAthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';

export interface IAthleteActivityResult {
  id?: number;
  value?: number;
  compareValue?: number;
  athleteActivityResultSplits?: IAthleteActivityResultSplit[];
  activityResultName?: string;
  activityResultId?: number;
  athleteActivityId?: number;
}

export class AthleteActivityResult implements IAthleteActivityResult {
  constructor(
    public id?: number,
    public value?: number,
    public compareValue?: number,
    public athleteActivityResultSplits?: IAthleteActivityResultSplit[],
    public activityResultName?: string,
    public activityResultId?: number,
    public athleteActivityId?: number
  ) {}
}
