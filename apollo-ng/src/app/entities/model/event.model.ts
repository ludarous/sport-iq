import { Moment } from 'moment';
import {IAthleteEvent} from './athlete-event.model';
import {IWorkout} from './workout.model';
import {IAthlete, Sex} from './athlete.model';
import * as moment from 'moment';

export interface IEvent {
    id?: number;
    name?: string;
    date?: Moment;
    athleteEvents?: IAthleteEvent[];
    addressStreet?: string;
    addressId?: number;
    tests?: IWorkout[];
    athletes?: IAthlete[];
}

export class Event implements IEvent {
    constructor(
        public id?: number,
        public name?: string,
        public date?: Moment,
        public athleteEvents?: IAthleteEvent[],
        public addressStreet?: string,
        public addressId?: number,
        public tests?: IWorkout[],
        public athletes?: IAthlete[]
    ) {}

    static parseItemEnums(event: IEvent): IEvent {
        if (event) {
            if (event.date) {
                event.date = moment(event.date);
            }
        }
        return event;
    }
}
