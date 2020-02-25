import { IActivity } from 'app/shared/model/activity.model';
import { IWorkoutCategory } from 'app/shared/model/workout-category.model';
import { ISport } from 'app/shared/model/sport.model';
import { IEvent } from 'app/shared/model/event.model';

export interface IWorkout {
  id?: number;
  name?: string;
  description?: string;
  activities?: IActivity[];
  categories?: IWorkoutCategory[];
  sports?: ISport[];
  events?: IEvent[];
}

export class Workout implements IWorkout {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public activities?: IActivity[],
    public categories?: IWorkoutCategory[],
    public sports?: ISport[],
    public events?: IEvent[]
  ) {}
}
