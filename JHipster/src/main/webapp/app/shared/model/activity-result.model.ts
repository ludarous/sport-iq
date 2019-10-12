import { IActivityResultSplit } from 'app/shared/model//activity-result-split.model';

export const enum ResultType {
    LESS_IS_BETTER = 'LESS_IS_BETTER',
    MORE_IS_BETTER = 'MORE_IS_BETTER'
}

export interface IActivityResult {
    id?: number;
    name?: string;
    resultType?: ResultType;
    ratingWeight?: number;
    activityId?: number;
    resultSplits?: IActivityResultSplit[];
    resultUnitName?: string;
    resultUnitId?: number;
}

export class ActivityResult implements IActivityResult {
    constructor(
        public id?: number,
        public name?: string,
        public resultType?: ResultType,
        public ratingWeight?: number,
        public activityId?: number,
        public resultSplits?: IActivityResultSplit[],
        public resultUnitName?: string,
        public resultUnitId?: number
    ) {}
}
