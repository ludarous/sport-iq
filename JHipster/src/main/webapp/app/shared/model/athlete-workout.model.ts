import { IAthleteActivity } from 'app/shared/model/athlete-activity.model';

export interface IAthleteWorkout {
  id?: number;
  note?: string;
  athleteActivities?: IAthleteActivity[];
  workoutName?: string;
  workoutId?: number;
  athleteEventId?: number;
}

export class AthleteWorkout implements IAthleteWorkout {
  constructor(
    public id?: number,
    public note?: string,
    public athleteActivities?: IAthleteActivity[],
    public workoutName?: string,
    public workoutId?: number,
    public athleteEventId?: number
  ) {}
}
