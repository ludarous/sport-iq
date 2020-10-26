export interface IActivityCategory {
    id?: number;
    name?: string;
    description?: string;
    childActivityCategories?: IActivityCategory[];
    parentActivityCategoryId?: number;
}

export class ActivityCategory implements IActivityCategory {
    // constructor(
    //     public id?: number,
    //     public name?: string,
    //     public description?: string,
    //     public childActivityCategories?: IActivityCategory[],
    //     public parentActivityCategoryId?: number
    // ) {}

    id?: number;
    name?: string;
    description?: string;
    childActivityCategories?: IActivityCategory[];
    parentActivityCategoryId?: number;
}