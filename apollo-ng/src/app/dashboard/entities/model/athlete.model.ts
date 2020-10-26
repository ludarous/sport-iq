import * as moment from 'moment';
import { Moment } from 'moment';
import { EnumWrapper } from '../../../modules/entities/enum-wrapper';
import { ISport } from './sport.model';
import { IEvent } from './event.model';
import { Laterality } from './enums/laterality.enum';

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
    phone?: string;
    birthDate?: Date;
    nationality?: string;
    sex?: Sex;
    country?: string;
    city?: string;
    street?: string;
    zipCode?: string;
    handLaterality?: Laterality;
    footLaterality?: Laterality;
    steppingFoot?: Laterality;
    termsAgreement?: boolean;
    gdprAgreement?: boolean;
    photographyAgreement?: boolean;
    medicalFitnessAgreement?: boolean;
    marketingAgreement?: boolean;
    lrFirstName?: string;
    lrLastName?: string;
    lrEmail?: string;
    lrPhone?: string;
    profileCompleted?: boolean;
    userId?: string;
    sports?: ISport[];
    events?: IEvent[];
}

export class Athlete implements IAthlete {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phone?: string,
        public birthDate?: Date,
        public nationality?: string,
        public sex?: Sex,
        public country?: string,
        public city?: string,
        public street?: string,
        public zipCode?: string,
        public handLaterality?: Laterality,
        public footLaterality?: Laterality,
        public steppingFoot?: Laterality,
        public termsAgreement?: boolean,
        public gdprAgreement?: boolean,
        public photographyAgreement?: boolean,
        public medicalFitnessAgreement?: boolean,
        public marketingAgreement?: boolean,
        public lrFirstName?: string,
        public lrLastName?: string,
        public lrEmail?: string,
        public lrPhone?: string,
        public profileCompleted?: boolean,
        public userId?: string,
        public sports?: ISport[],
        public events?: IEvent[]
    ) {
        this.termsAgreement = this.termsAgreement || false;
        this.gdprAgreement = this.gdprAgreement || false;
        this.photographyAgreement = this.photographyAgreement || false;
        this.medicalFitnessAgreement = this.medicalFitnessAgreement || false;
        this.marketingAgreement = this.marketingAgreement || false;
        this.profileCompleted = this.profileCompleted || false;
    }

    get name(): string {
        return this.firstName + ' ' + this.lastName;
    }

    static parseItemEnums(athlete: IAthlete): IAthlete {
        if (athlete) {
            if (athlete.birthDate) {
                athlete.birthDate = new Date(athlete.birthDate);
            }

            if (athlete.sex) {
                athlete.sex = Sex.get(athlete.sex);
            }

            if (athlete.handLaterality) {
                athlete.handLaterality = Laterality.get(athlete.handLaterality);
            }

            if (athlete.footLaterality) {
                athlete.footLaterality = Laterality.get(athlete.footLaterality);
            }

            if (athlete.steppingFoot) {
                athlete.steppingFoot = Laterality.get(athlete.steppingFoot);
            }
        }
        return athlete;
    }
}
