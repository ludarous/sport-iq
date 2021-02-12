import { Moment } from 'moment';

export interface IBodyCharacteristics {
  id?: number;
  height?: number;
  weight?: number;
  date?: Moment;
  heightUnitId?: number;
  widthUnitId?: number;
}

export class BodyCharacteristics implements IBodyCharacteristics {
  constructor(
    public id?: number,
    public height?: number,
    public weight?: number,
    public date?: Moment,
    public heightUnitId?: number,
    public widthUnitId?: number
  ) {}
}
