import { Moment } from 'moment';
import { IGroupMembership } from 'app/shared/model/group-membership.model';
import { IActivity } from 'app/shared/model/activity.model';

export interface IGroup {
  id?: number;
  name?: string;
  created?: Moment;
  description?: string;
  memberships?: IGroupMembership[];
  ownerId?: number;
  visibleActivities?: IActivity[];
}

export class Group implements IGroup {
  constructor(
    public id?: number,
    public name?: string,
    public created?: Moment,
    public description?: string,
    public memberships?: IGroupMembership[],
    public ownerId?: number,
    public visibleActivities?: IActivity[]
  ) {}
}
