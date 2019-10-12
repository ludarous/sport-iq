import { IAthleteWorkout } from 'app/shared/model//athlete-workout.model';

export interface IAthleteEvent {
    id?: number;
    note?: string;
    actualHeightInCm?: number;
    actualWeightInKg?: number;
    eventId?: number;
    athleteWorkouts?: IAthleteWorkout[];
    athleteId?: number;
}

export class AthleteEvent implements IAthleteEvent {
    constructor(
        public id?: number,
        public note?: string,
        public actualHeightInCm?: number,
        public actualWeightInKg?: number,
        public eventId?: number,
        public athleteWorkouts?: IAthleteWorkout[],
        public athleteId?: number
    ) {}
}
