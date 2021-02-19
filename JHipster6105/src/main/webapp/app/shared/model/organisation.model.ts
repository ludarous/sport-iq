import { Moment } from 'moment';
import { IOrganisationMembership } from 'app/shared/model/organisation-membership.model';
import { IUser } from 'app/core/user/user.model';
import { IActivity } from 'app/shared/model/activity.model';

export interface IOrganisation {
  id?: number;
  name?: string;
  created?: Moment;
  description?: string;
  memberships?: IOrganisationMembership[];
  owner?: IUser;
  visibleActivities?: IActivity[];
}

export class Organisation implements IOrganisation {
  constructor(
    public id?: number,
    public name?: string,
    public created?: Moment,
    public description?: string,
    public memberships?: IOrganisationMembership[],
    public owner?: IUser,
    public visibleActivities?: IActivity[]
  ) {}
}
