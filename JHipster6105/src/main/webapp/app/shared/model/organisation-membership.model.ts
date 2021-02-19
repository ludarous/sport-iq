import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IMembershipRole } from 'app/shared/model/membership-role.model';
import { IOrganisation } from 'app/shared/model/organisation.model';

export interface IOrganisationMembership {
  id?: number;
  created?: Moment;
  user?: IUser;
  roles?: IMembershipRole[];
  organisation?: IOrganisation;
}

export class OrganisationMembership implements IOrganisationMembership {
  constructor(
    public id?: number,
    public created?: Moment,
    public user?: IUser,
    public roles?: IMembershipRole[],
    public organisation?: IOrganisation
  ) {}
}
