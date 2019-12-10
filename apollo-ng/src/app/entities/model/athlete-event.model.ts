import {IAthleteWorkout} from './athlete-workout.model';

export interface IAthleteEvent {
    id?: number;
    note?: string;
    actualHeightInCm?: number;
    actualWeightInKg?: number;
    eventId?: number;
    eventName?: string;
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
        public eventName?: string,
        public athleteWorkouts?: IAthleteWorkout[],
        public athleteId?: number
    ) {}
}
