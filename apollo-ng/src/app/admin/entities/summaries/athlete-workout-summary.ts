import {AthleteActivityResultSummary} from './athlete-activity-result-summary';
import {IActivity} from '../model/activity.model';
import {IAthleteActivity} from '../model/athlete-activity.model';
import {IAthleteWorkout} from '../model/athlete-workout.model';
import {IWorkout} from '../model/workout.model';
import {AthleteActivitySummary} from './athlete-activity-summary';

export interface AthleteWorkoutSummary {
    athleteWorkout?: IAthleteWorkout;
    workout?: IWorkout;
    activitySummaries?: Array<AthleteActivitySummary>;
}
