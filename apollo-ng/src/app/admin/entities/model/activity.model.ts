import {ActivityResult, IActivityResult} from './activity-result.model';
import {ActivityCategory, IActivityCategory} from './activity-category.model';
import {numeric, required} from '@rxweb/reactive-form-validators';
import {AthleteActivitySummary} from '../summaries/athlete-activity-summary';

export interface IActivity {
    id?: number;
    name?: string;
    description?: string;
    help?: string;
    minAge?: number;
    maxAge?: number;
    targetValue?: number;
    activityResults?: IActivityResult[];
    targetUnitName?: string;
    targetUnitId?: number;
    categories?: IActivityCategory[];
}

export class Activity implements IActivity {

    id: number = null;
    @required()
    name: string = null;
    description: string = null;
    help: string = null;
    @numeric()
    minAge: number = null;
    @numeric()
    maxAge: number = null;
    targetValue: number = null;
    activityResults: IActivityResult[] = new Array<IActivityResult>();
    targetUnitName: string = null;
    targetUnitId: number = null;
    categories: IActivityCategory[] = new Array<IActivityCategory>();

    static parseItemEnums(activity: IActivity): IActivity {
        if (activity) {
            if (activity.activityResults) {
                for (const result of activity.activityResults) {
                    ActivityResult.parseItemEnums(result);
                }
            }
        }
        return activity;
    }

    static sortActivitySummariesByActivityId(items: Array<AthleteActivitySummary>, order = 1) {
        if (items) {
            const comparer = (o1: AthleteActivitySummary, o2: AthleteActivitySummary) => {
                const itemOrderResult = o1.activity.id - o2.activity.id;
                return itemOrderResult * order;
            };
            return items.sort(comparer);
        }
        return items;
    }
}
