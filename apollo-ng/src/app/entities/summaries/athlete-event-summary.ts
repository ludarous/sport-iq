import {AthleteWorkoutSummary} from './athlete-workout-summary';
import {IAthleteEvent} from '../model/athlete-event.model';
import {IEvent} from '../model/event.model';

export interface AthleteEventSummary {
    athleteEvent?: IAthleteEvent;
    event?: IEvent;
    workoutSummaries?: Array<AthleteWorkoutSummary>;
}
