import { IActivityResult } from 'app/shared/model/activity-result.model';
import { IUser } from 'app/core/user/user.model';
import { IOrganisation } from 'app/shared/model/organisation.model';
import { IGroup } from 'app/shared/model/group.model';
import { IEvent } from 'app/shared/model/event.model';

export interface IActivity {
  id?: number;
  name?: string;
  isPublic?: boolean;
  description?: string;
  help?: string;
  activityResults?: IActivityResult[];
  createdBy?: IUser;
  visibleForOrganisations?: IOrganisation[];
  visibleForGroups?: IGroup[];
  events?: IEvent[];
}

export class Activity implements IActivity {
  constructor(
    public id?: number,
    public name?: string,
    public isPublic?: boolean,
    public description?: string,
    public help?: string,
    public activityResults?: IActivityResult[],
    public createdBy?: IUser,
    public visibleForOrganisations?: IOrganisation[],
    public visibleForGroups?: IGroup[],
    public events?: IEvent[]
  ) {
    this.isPublic = this.isPublic || false;
  }
}
