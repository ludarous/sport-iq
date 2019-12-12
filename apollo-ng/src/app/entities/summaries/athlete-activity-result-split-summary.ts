import {IAthleteActivityResultSplit} from '../model/athlete-activity-result-split.model';
import {IActivityResultSplit} from '../model/activity-result-split.model';
import {Stats} from './stats';

export interface AthleteActivityResultSplitSummary {
    athleteActivityResultSplit?: IAthleteActivityResultSplit;
    activityResultSplit?: IActivityResultSplit;
    stats?: Stats;
}
