import { Moment } from 'moment';

export const enum Sex {
    MALE = 'MALE',
    FEMALE = 'FEMALE',
    OTHER = 'OTHER'
}

export interface IAthlete {
    id?: number;
    firstName?: string;
    lastName?: string;
    email?: string;
    birthDate?: Moment;
    nationality?: string;
    sex?: Sex;
    addressId?: number;
}

export class Athlete implements IAthlete {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public birthDate?: Moment,
        public nationality?: string,
        public sex?: Sex,
        public addressId?: number
    ) {}
}