export interface IUnit {
  id?: number;
  name?: string;
  abbreviation?: string;
}

export class Unit implements IUnit {
  constructor(public id?: number, public name?: string, public abbreviation?: string) {}
}
