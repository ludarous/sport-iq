import { IUnit } from 'app/shared/model/unit.model';
import { IActivityResult } from 'app/shared/model/activity-result.model';

export interface IActivityResultSplit {
  id?: number;
  splitValue?: number;
  splitUnit?: IUnit;
  activityResult?: IActivityResult;
}

export class ActivityResultSplit implements IActivityResultSplit {
  constructor(public id?: number, public splitValue?: number, public splitUnit?: IUnit, public activityResult?: IActivityResult) {}
}
