import {Stats} from './stats';
import {IAthleteActivityResult} from '../model/athlete-activity-result.model';
import {IActivityResult} from '../model/activity-result.model';

export class AthleteActivityResultSummary {
    athleteActivityResult?: IAthleteActivityResult;
    activityResult?: IActivityResult;
    stats?: Stats;

    static sortByMainResult(input: Array<AthleteActivityResultSummary>, order = 1): Array<AthleteActivityResultSummary> {
        if (input) {
            const comparer = (o1: AthleteActivityResultSummary, o2: AthleteActivityResultSummary) => {
                const result =  (o1.activityResult.mainResult === o2.activityResult.mainResult) ? 0 : o1.activityResult.mainResult ? -1 : 1;
                return result * order;
            };
            return input.sort(comparer);
        }
        return input;
    }


}

