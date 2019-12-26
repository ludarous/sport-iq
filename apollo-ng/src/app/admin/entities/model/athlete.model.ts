import { Moment } from 'moment';
import * as moment from 'moment';
import {EnumWrapper} from '../../modules/entities/enum-wrapper';
import {ResultTypeEnum} from './activity-result.model';

export enum SexEnum {
    MALE,
    FEMALE,
    OTHER
}

export class Sex extends EnumWrapper {
    constructor(value: number | string) {
        super(SexEnum, value);
    }

    public static MALE = new Sex(SexEnum.MALE);
    public static FEMALE = new Sex(SexEnum.FEMALE);
    public static OTHER = new Sex(SexEnum.OTHER);


    private static all: Array<Sex> = new Array<Sex>(
        Sex.MALE,
        Sex.FEMALE,
        Sex.OTHER
    );

    public static getAll(): Array<Sex> {
        return Sex.all;
    }

    public static get(value: number | string | EnumWrapper): Sex {
        if (value instanceof EnumWrapper) {
            return value;
        } else {
            return this.all.find(e => e.ordinal === value || e.value === value);
        }
    }

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

    name: string;
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

    get name(): string {
        return this.firstName + ' ' + this.lastName;
    }

    static parseItemEnums(athlete: IAthlete): IAthlete {
        if (athlete) {
            if (athlete.birthDate) {
                athlete.birthDate = moment(athlete.birthDate);
            }

            if (athlete.sex) {
                athlete.sex = Sex.get(athlete.sex);
            }
        }
        return athlete;
    }
}
