import { IAthleteWorkout } from 'app/shared/model/athlete-workout.model';

export interface IAthleteEvent {
  id?: number;
  note?: string;
  actualHeightInCm?: number;
  actualWeightInKg?: number;
  athleteWorkouts?: IAthleteWorkout[];
  athleteId?: number;
  eventId?: number;
}

export class AthleteEvent implements IAthleteEvent {
  constructor(
    public id?: number,
    public note?: string,
    public actualHeightInCm?: number,
    public actualWeightInKg?: number,
    public athleteWorkouts?: IAthleteWorkout[],
    public athleteId?: number,
    public eventId?: number
  ) {}
}
