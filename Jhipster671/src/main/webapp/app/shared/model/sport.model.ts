import { IAthlete } from 'app/shared/model/athlete.model';
import { IWorkout } from 'app/shared/model/workout.model';

export interface ISport {
  id?: number;
  name?: string;
  athletes?: IAthlete[];
  workouts?: IWorkout[];
}

export class Sport implements ISport {
  constructor(public id?: number, public name?: string, public athletes?: IAthlete[], public workouts?: IWorkout[]) {}
}
