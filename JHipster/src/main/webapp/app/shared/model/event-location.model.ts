export interface IEventLocation {
  id?: number;
  name?: string;
  state?: string;
  city?: string;
  street?: string;
  streetNumber?: string;
  latitude?: number;
  longitude?: number;
  mapLink?: string;
  capacity?: number;
  maxAge?: number;
  minAge?: number;
}

export class EventLocation implements IEventLocation {
  constructor(
    public id?: number,
    public name?: string,
    public state?: string,
    public city?: string,
    public street?: string,
    public streetNumber?: string,
    public latitude?: number,
    public longitude?: number,
    public mapLink?: string,
    public capacity?: number,
    public maxAge?: number,
    public minAge?: number
  ) {}
}
