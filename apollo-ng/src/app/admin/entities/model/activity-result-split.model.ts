import {IUnit} from './unit.model';

export interface IActivityResultSplit {
    id?: number;
    splitValue?: number;
    activityResultId?: number;
    splitUnit?: IUnit;
}

export class ActivityResultSplit implements IActivityResultSplit {
    constructor(activityResultId: number, selectedUnit: IUnit) {
        this.activityResultId = activityResultId;
        this.splitUnit = selectedUnit;
    }

    id?: number;
    splitValue?: number;
    activityResultId?: number;
    splitUnit?: IUnit;
}
