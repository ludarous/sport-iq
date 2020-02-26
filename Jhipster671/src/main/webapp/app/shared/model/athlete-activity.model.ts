import { Moment } from 'moment';
import { IAthleteActivityResult } from 'app/shared/model/athlete-activity-result.model';

export interface IAthleteActivity {
  id?: number;
  note?: string;
  date?: Moment;
  athleteActivityResults?: IAthleteActivityResult[];
  activityName?: string;
  activityId?: number;
  athleteWorkoutId?: number;
}

export class AthleteActivity implements IAthleteActivity {
  constructor(
    public id?: number,
    public note?: string,
    public date?: Moment,
    public athleteActivityResults?: IAthleteActivityResult[],
    public activityName?: string,
    public activityId?: number,
    public athleteWorkoutId?: number
  ) {}
}
