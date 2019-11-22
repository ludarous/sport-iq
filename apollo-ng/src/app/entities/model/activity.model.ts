import {IActivityResult} from './activity-result.model';
import {ActivityCategory, IActivityCategory} from './activity-category.model';
import {required} from '@rxweb/reactive-form-validators';

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
    minAge: number = null;
    maxAge: number = null;
    targetValue: number = null;
    activityResults: IActivityResult[] = new Array<IActivityResult>();
    targetUnitName: string = null;
    targetUnitId: number = null;
    categories: IActivityCategory[] = new Array<IActivityCategory>();

    static parseItemEnums(activity: any): IActivity {
        if (activity) {

        }
        return activity;
    }
}
