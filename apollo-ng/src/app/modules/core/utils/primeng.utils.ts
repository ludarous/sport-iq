import {SelectItem} from 'primeng/api';
import {MultiSelectItem} from 'primeng/primeng';

export class PrimengUtils {
    static getSelectItem(item: any, valueField: string, labelField: string, mapLabel?: (v) => string): SelectItem {
        return {value: item[valueField], label: item[labelField]};
    }

    static getMultiSelectItem(item: any, valueField: string, labelField: string): any {
        return {
            option: this.getSelectItem(item, valueField, labelField),
            selected: false,
            disabled: false,
            visible: true
        };
    }

}
