import { Moment } from 'moment';
import { IMembershipRole } from 'app/shared/model/membership-role.model';

export interface IGroupMembership {
  id?: number;
  created?: Moment;
  userId?: number;
  roles?: IMembershipRole[];
  groupId?: number;
}

export class GroupMembership implements IGroupMembership {
  constructor(
    public id?: number,
    public created?: Moment,
    public userId?: number,
    public roles?: IMembershipRole[],
    public groupId?: number
  ) {}
}
