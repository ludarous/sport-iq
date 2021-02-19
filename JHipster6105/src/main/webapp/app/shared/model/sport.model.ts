export interface ISport {
  id?: number;
  name?: string;
}

export class Sport implements ISport {
  constructor(public id?: number, public name?: string) {}
}
