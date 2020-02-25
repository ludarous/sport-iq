export interface IActivityResultSplit {
  id?: number;
  splitValue?: number;
  splitUnitName?: string;
  splitUnitId?: number;
  activityResultId?: number;
}

export class ActivityResultSplit implements IActivityResultSplit {
  constructor(
    public id?: number,
    public splitValue?: number,
    public splitUnitName?: string,
    public splitUnitId?: number,
    public activityResultId?: number
  ) {}
}
