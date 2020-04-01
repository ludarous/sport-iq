import { Moment } from 'moment';
import {IAthleteEvent} from './athlete-event.model';
import {IWorkout} from './workout.model';
import {IAthlete, Sex} from './athlete.model';
import * as moment from 'moment';
import { IEventLocation } from './event-location.model';

export interface IEvent {
    id?: number;
    name?: string;
    date?: Moment;
    athleteEvents?: IAthleteEvent[];
    eventLocation?: IEventLocation;
    tests?: IWorkout[];
    athletes?: IAthlete[];
}

export class Event implements IEvent {

    id: number = null;
    name: string = null;
    date: Moment = null;
    athleteEvents: IAthleteEvent[] = new Array<IAthleteEvent>();
    eventLocation: IEventLocation;
    tests: IWorkout[] = new Array<IWorkout>();
    athletes: IAthlete[] = new Array<IAthlete>();

    static parseItemEnums(event: IEvent): IEvent {
        if (event) {
            if (event.date) {
                event.date = moment(event.date);
            }
        }
        return event;
    }
}
