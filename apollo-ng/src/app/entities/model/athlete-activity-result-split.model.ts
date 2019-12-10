export interface IAthleteActivityResultSplit {
    id?: number;
    value?: number;
    compareValue?: number;
    athleteActivityResultId?: number;
    activityResultSplitId?: number;
}

export class AthleteActivityResultSplit implements IAthleteActivityResultSplit {
    constructor(
        public id?: number,
        public value?: number,
        public compareValue?: number,
        public athleteActivityResultId?: number,
        public activityResultSplitId?: number
    ) {}

}
