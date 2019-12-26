import {IAthleteActivityResultSplit} from '../model/athlete-activity-result-split.model';
import {IActivityResultSplit} from '../model/activity-result-split.model';
import {Stats} from './stats';
import {AthleteActivityResultSplitSummary} from './athlete-activity-result-split-summary';
import {IAthleteActivityResult} from '../model/athlete-activity-result.model';
import {IActivityResult} from '../model/activity-result.model';

export interface AthleteActivityResultSummary {
    athleteActivityResult?: IAthleteActivityResult;
    activityResult?: IActivityResult;
    resultSplitSummaries?: Array<AthleteActivityResultSplitSummary>;
    stats?: Stats;
}
