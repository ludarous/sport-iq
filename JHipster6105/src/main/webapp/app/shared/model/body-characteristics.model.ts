import { Moment } from 'moment';
import { IUnit } from 'app/shared/model/unit.model';

export interface IBodyCharacteristics {
  id?: number;
  height?: number;
  weight?: number;
  date?: Moment;
  heightUnit?: IUnit;
  widthUnit?: IUnit;
}

export class BodyCharacteristics implements IBodyCharacteristics {
  constructor(
    public id?: number,
    public height?: number,
    public weight?: number,
    public date?: Moment,
    public heightUnit?: IUnit,
    public widthUnit?: IUnit
  ) {}
}
