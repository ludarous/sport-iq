import { IActivityResult } from 'app/shared/model/activity-result.model';
import { IActivityCategory } from 'app/shared/model/activity-category.model';
import { IWorkout } from 'app/shared/model/workout.model';

export interface IActivity {
  id?: number;
  name?: string;
  description?: string;
  help?: string;
  minAge?: number;
  maxAge?: number;
  activityResults?: IActivityResult[];
  targetUnitName?: string;
  targetUnitId?: number;
  categories?: IActivityCategory[];
  workouts?: IWorkout[];
}

export class Activity implements IActivity {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public help?: string,
    public minAge?: number,
    public maxAge?: number,
    public activityResults?: IActivityResult[],
    public targetUnitName?: string,
    public targetUnitId?: number,
    public categories?: IActivityCategory[],
    public workouts?: IWorkout[]
  ) {}
}
