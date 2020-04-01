import {IActivityResultSplit} from './activity-result-split.model';
import {IUnit} from './unit.model';
import {EnumWrapper} from '../../../modules/entities/enum-wrapper';
import {IActivity} from './activity.model';

export enum ResultTypeEnum {
    LESS_IS_BETTER,
    MORE_IS_BETTER
}

export class ResultType extends EnumWrapper {
    constructor(value: number | string) {
        super(ResultTypeEnum, value);
    }

    public static LESS_IS_BETTER = new ResultType(ResultTypeEnum.LESS_IS_BETTER);
    public static MORE_IS_BETTER = new ResultType(ResultTypeEnum.MORE_IS_BETTER);


    private static all: Array<ResultType> = new Array<ResultType>(
        ResultType.LESS_IS_BETTER,
        ResultType.MORE_IS_BETTER,
    );

    public static getAll(): Array<ResultType> {
        return ResultType.all;
    }

    public static get(value: number | string | EnumWrapper): ResultType {
        if (value instanceof EnumWrapper) {
            return value;
        } else {
            return this.all.find(e => e.ordinal === value || e.value === value);
        }
    }

}

export interface IActivityResult {
    id?: number;
    name?: string;
    resultType?: ResultType;
    ratingWeight?: number;
    activityId?: number;
    resultSplits?: IActivityResultSplit[];
    resultUnit?: IUnit;
    mainResult?: boolean;
}

export class ActivityResult implements IActivityResult {
    id?: number;
    name?: string;
    resultType?: ResultType;
    ratingWeight?: number;
    activityId?: number;
    resultSplits?: IActivityResultSplit[] = new Array<IActivityResultSplit>();
    resultUnit?: IUnit;
    mainResult?: boolean;

    static parseItemEnums(activityResult: IActivityResult): IActivityResult {
        if (activityResult) {
            if (activityResult.resultType) {
               activityResult.resultType = ResultType.get(activityResult.resultType);
            }
        }
        return activityResult;
    }
}
