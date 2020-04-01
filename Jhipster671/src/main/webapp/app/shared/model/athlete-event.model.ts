import { Moment } from 'moment';
import { IAthleteWorkout } from 'app/shared/model/athlete-workout.model';

export interface IAthleteEvent {
  id?: number;
  note?: string;
  actualHeightInCm?: number;
  actualWeightInKg?: number;
  medicalFitnessAgreement?: boolean;
  registrationDate?: Moment;
  athleteWorkouts?: IAthleteWorkout[];
  athleteId?: number;
  eventId?: number;
}

export class AthleteEvent implements IAthleteEvent {
  constructor(
    public id?: number,
    public note?: string,
    public actualHeightInCm?: number,
    public actualWeightInKg?: number,
    public medicalFitnessAgreement?: boolean,
    public registrationDate?: Moment,
    public athleteWorkouts?: IAthleteWorkout[],
    public athleteId?: number,
    public eventId?: number
  ) {
    this.medicalFitnessAgreement = this.medicalFitnessAgreement || false;
  }
}
