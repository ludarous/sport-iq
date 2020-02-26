import { IWorkout } from 'app/shared/model/workout.model';

export interface ISport {
  id?: number;
  name?: string;
  workouts?: IWorkout[];
}

export class Sport implements ISport {
  constructor(public id?: number, public name?: string, public workouts?: IWorkout[]) {}
}
