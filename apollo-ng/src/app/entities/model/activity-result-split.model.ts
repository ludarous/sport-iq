export interface IActivityResultSplit {
    id?: number;
    splitValue?: number;
    activityResultId?: number;
    splitUnitName?: string;
    splitUnitId?: number;
}

export class ActivityResultSplit implements IActivityResultSplit {
    constructor(
        public id?: number,
        public splitValue?: number,
        public activityResultId?: number,
        public splitUnitName?: string,
        public splitUnitId?: number
    ) {}
}
