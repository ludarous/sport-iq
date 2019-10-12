export interface IActivityCategory {
    id?: number;
    name?: string;
    description?: string;
}

export class ActivityCategory implements IActivityCategory {
    constructor(public id?: number, public name?: string, public description?: string) {}
}
