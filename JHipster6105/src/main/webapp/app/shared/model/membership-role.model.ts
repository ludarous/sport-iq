import { IOrganisationMembership } from 'app/shared/model/organisation-membership.model';
import { IGroupMembership } from 'app/shared/model/group-membership.model';

export interface IMembershipRole {
  id?: number;
  name?: string;
  organisationMemberships?: IOrganisationMembership[];
  groupMemberships?: IGroupMembership[];
}

export class MembershipRole implements IMembershipRole {
  constructor(
    public id?: number,
    public name?: string,
    public organisationMemberships?: IOrganisationMembership[],
    public groupMemberships?: IGroupMembership[]
  ) {}
}
