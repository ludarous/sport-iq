export interface IAthleteActivityResultSplit {
    id?: number;
    value?: number;
    athleteActivityResultId?: number;
}

export class AthleteActivityResultSplit implements IAthleteActivityResultSplit {
    constructor(public id?: number, public value?: number, public athleteActivityResultId?: number) {}
}
