import {IUnit} from './unit.model';
import {AthleteActivityResultSplitSummary} from '../summaries/athlete-activity-result-split-summary';
import {AthleteActivitySummary} from '../summaries/athlete-activity-summary';

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

    static sortSummariesByActivityResultSplitValue(items: Array<AthleteActivityResultSplitSummary>, order = 1) {
        if (items) {
            const comparer = (o1: AthleteActivityResultSplitSummary, o2: AthleteActivityResultSplitSummary) => {
                const itemOrderResult = o1.activityResultSplit.splitValue - o2.activityResultSplit.splitValue;
                return itemOrderResult * order;
            };
            return items.sort(comparer);
        }
        return items;
    }
}
