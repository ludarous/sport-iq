export interface IAddress {
    id?: number;
    country?: string;
    city?: string;
    street?: string;
    zipCode?: string;
}

export class Address implements IAddress {
    constructor(public id?: number, public country?: string, public city?: string, public street?: string, public zipCode?: string) {}
}
