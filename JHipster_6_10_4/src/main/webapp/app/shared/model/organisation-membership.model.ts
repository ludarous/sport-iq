import { Moment } from 'moment';
import { IMembershipRole } from 'app/shared/model/membership-role.model';

export interface IOrganisationMembership {
  id?: number;
  created?: Moment;
  userId?: number;
  roles?: IMembershipRole[];
  organisationId?: number;
}

export class OrganisationMembership implements IOrganisationMembership {
  constructor(
    public id?: number,
    public created?: Moment,
    public userId?: number,
    public roles?: IMembershipRole[],
    public organisationId?: number
  ) {}
}
