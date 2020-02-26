import { IActivity } from 'app/shared/model/activity.model';

export interface IActivityCategory {
  id?: number;
  name?: string;
  description?: string;
  childActivityCategories?: IActivityCategory[];
  parentActivityCategoryId?: number;
  activities?: IActivity[];
}

export class ActivityCategory implements IActivityCategory {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public childActivityCategories?: IActivityCategory[],
    public parentActivityCategoryId?: number,
    public activities?: IActivity[]
  ) {}
}
