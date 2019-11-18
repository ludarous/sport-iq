import {IActivityResult} from './activity-result.model';
import {IActivityCategory} from './activity-category.model';

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
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public help?: string,
        public minAge?: number,
        public maxAge?: number,
        public targetValue?: number,
        public activityResults?: IActivityResult[],
        public targetUnitName?: string,
        public targetUnitId?: number,
        public categories?: IActivityCategory[]
    ) {}

    static parseItemEnums(activity: any): IActivity {
        if (activity) {

        }
        return activity;
    }
}
