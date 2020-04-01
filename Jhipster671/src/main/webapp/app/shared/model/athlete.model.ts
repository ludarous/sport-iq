import { Moment } from 'moment';
import { ISport } from 'app/shared/model/sport.model';
import { IEvent } from 'app/shared/model/event.model';
import { Sex } from 'app/shared/model/enumerations/sex.model';
import { Laterality } from 'app/shared/model/enumerations/laterality.model';

export interface IAthlete {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  phone?: string;
  birthDate?: Moment;
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
  userId?: number;
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
    public birthDate?: Moment,
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
    public userId?: number,
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
}
