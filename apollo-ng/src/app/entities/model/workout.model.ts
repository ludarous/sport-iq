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
    constructor(
        public id?: number,
        public name?: string,
        public description?: string,
        public activities?: IActivity[],
        public categories?: IWorkoutCategory[],
        public sports?: ISport[]
    ) {}
}
