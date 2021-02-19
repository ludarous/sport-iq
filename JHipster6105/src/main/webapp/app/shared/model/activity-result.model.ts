import { IActivityResultSplit } from 'app/shared/model/activity-result-split.model';
import { IUnit } from 'app/shared/model/unit.model';
import { IActivity } from 'app/shared/model/activity.model';
import { ResultType } from 'app/shared/model/enumerations/result-type.model';

export interface IActivityResult {
  id?: number;
  name?: string;
  resultType?: ResultType;
  ratingWeight?: number;
  mainResult?: boolean;
  order?: number;
  irvBest?: number;
  irvWorst?: number;
  resultSplits?: IActivityResultSplit[];
  resultUnit?: IUnit;
  activity?: IActivity;
}

export class ActivityResult implements IActivityResult {
  constructor(
    public id?: number,
    public name?: string,
    public resultType?: ResultType,
    public ratingWeight?: number,
    public mainResult?: boolean,
    public order?: number,
    public irvBest?: number,
    public irvWorst?: number,
    public resultSplits?: IActivityResultSplit[],
    public resultUnit?: IUnit,
    public activity?: IActivity
  ) {
    this.mainResult = this.mainResult || false;
  }
}
