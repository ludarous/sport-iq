import { IAthleteActivity } from 'app/shared/model//athlete-activity.model';

export interface IAthleteWorkout {
    id?: number;
    note?: string;
    athleteEventId?: number;
    athleteActivities?: IAthleteActivity[];
    workoutName?: string;
    workoutId?: number;
}

export class AthleteWorkout implements IAthleteWorkout {
    constructor(
        public id?: number,
        public note?: string,
        public athleteEventId?: number,
        public athleteActivities?: IAthleteActivity[],
        public workoutName?: string,
        public workoutId?: number
    ) {}
}
