import { IActivityResultSplit } from 'app/shared/model/activity-result-split.model';
import { ResultType } from 'app/shared/model/enumerations/result-type.model';

export interface IActivityResult {
  id?: number;
  name?: string;
  resultType?: ResultType;
  ratingWeight?: number;
  resultSplits?: IActivityResultSplit[];
  resultUnitName?: string;
  resultUnitId?: number;
  activityId?: number;
}

export class ActivityResult implements IActivityResult {
  constructor(
    public id?: number,
    public name?: string,
    public resultType?: ResultType,
    public ratingWeight?: number,
    public resultSplits?: IActivityResultSplit[],
    public resultUnitName?: string,
    public resultUnitId?: number,
    public activityId?: number
  ) {}
}