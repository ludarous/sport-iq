import {IActivity} from './activity.model';
import {IWorkoutCategory} from './workout-category.model';
import {ISport} from './sport.model';

export interface IWorkout {
    id?: number;
    name?: string;
    description?: string;
    activities?: IActivity[];
    categories?: IWorkoutCategory[];
    sports?: ISport[];
}

export class Workout implements IWorkout {
    id: number = null;
    name: string = null;
    description: string  = null;
    activities: IActivity[] = new Array<IActivity>();
    categories: IWorkoutCategory[] = new Array<IWorkoutCategory>();
    sports: ISport[] = new Array<ISport>();
}
