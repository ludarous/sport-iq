import { EnumWrapper } from '../../../../modules/entities/enum-wrapper';

export enum LateralityEnum {
    LEFT,
    RIGHT
}

export class Laterality extends EnumWrapper {
    constructor(value: number | string) {
        super(LateralityEnum, value);
    }

    public static LEFT = new Laterality(LateralityEnum.LEFT);
    public static RIGHT = new Laterality(LateralityEnum.RIGHT);

    private static all: Array<Laterality> = new Array<Laterality>(
        Laterality.LEFT,
        Laterality.RIGHT
    );

    public static getAll(): Array<Laterality> {
        return Laterality.all;
    }

    public static get(value: number | string | EnumWrapper): Laterality {
        if (value instanceof EnumWrapper) {
            return value;
        } else {
            return this.all.find(e => e.ordinal === value || e.value === value);
        }
    }

}
