export interface IEventLocation {
  id?: number;
  name?: string;
  state?: string;
  city?: string;
  street?: string;
  streetNumber?: string;
  latitude?: number;
  longitude?: number;
  capacity?: number;
  mapLink?: string;
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
    public capacity?: number,
    public mapLink?: string,
    public maxAge?: number,
    public minAge?: number
  ) {}
}
