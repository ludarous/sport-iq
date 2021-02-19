import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IMembershipRole } from 'app/shared/model/membership-role.model';
import { IGroup } from 'app/shared/model/group.model';

export interface IGroupMembership {
  id?: number;
  created?: Moment;
  user?: IUser;
  roles?: IMembershipRole[];
  group?: IGroup;
}

export class GroupMembership implements IGroupMembership {
  constructor(public id?: number, public created?: Moment, public user?: IUser, public roles?: IMembershipRole[], public group?: IGroup) {}
}
