import {AthleteActivityResultSummary} from './athlete-activity-result-summary';
import {IActivity} from '../model/activity.model';
import {IAthleteActivity} from '../model/athlete-activity.model';

export interface AthleteActivitySummary {
    athleteActivity?: IAthleteActivity;
    activity?: IActivity;
    resultSummaries?: Array<AthleteActivityResultSummary>;
}
