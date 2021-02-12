import { Moment } from 'moment';
import { IOrganisationMembership } from 'app/shared/model/organisation-membership.model';
import { IActivity } from 'app/shared/model/activity.model';

export interface IOrganisation {
  id?: number;
  name?: string;
  created?: Moment;
  description?: string;
  memberships?: IOrganisationMembership[];
  ownerId?: number;
  visibleActivities?: IActivity[];
}

export class Organisation implements IOrganisation {
  constructor(
    public id?: number,
    public name?: string,
    public created?: Moment,
    public description?: string,
    public memberships?: IOrganisationMembership[],
    public ownerId?: number,
    public visibleActivities?: IActivity[]
  ) {}
}
