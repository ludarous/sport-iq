export interface IWorkoutCategory {
    id?: number;
    name?: string;
    description?: string;
}

export class WorkoutCategory implements IWorkoutCategory {
    constructor(public id?: number, public name?: string, public description?: string) {}
}
