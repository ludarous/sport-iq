import { Moment } from 'moment';
import { IAthleteActivityResult } from 'app/shared/model//athlete-activity-result.model';

export interface IAthleteActivity {
    id?: number;
    note?: string;
    date?: Moment;
    athleteWorkoutId?: number;
    athleteActivityResults?: IAthleteActivityResult[];
    activityName?: string;
    activityId?: number;
}

export class AthleteActivity implements IAthleteActivity {
    constructor(
        public id?: number,
        public note?: string,
        public date?: Moment,
        public athleteWorkoutId?: number,
        public athleteActivityResults?: IAthleteActivityResult[],
        public activityName?: string,
        public activityId?: number
    ) {}
}
