import { IWorkout } from 'app/shared/model/workout.model';

export interface IWorkoutCategory {
  id?: number;
  name?: string;
  description?: string;
  workouts?: IWorkout[];
}

export class WorkoutCategory implements IWorkoutCategory {
  constructor(public id?: number, public name?: string, public description?: string, public workouts?: IWorkout[]) {}
}
