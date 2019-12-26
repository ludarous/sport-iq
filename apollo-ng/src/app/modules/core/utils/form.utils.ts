import {AbstractControl, FormArray, FormControl, FormGroup} from '@angular/forms';

export class FormUtils {
    static getFormGroup(item: any): FormGroup {
        const formGroup = new FormGroup({});

        Object.keys(item).forEach(key => {
            const value = item[key];
            if (value instanceof Array) {
                formGroup.addControl(key, FormUtils.getFormArray(value) as AbstractControl);
            } else if (value instanceof String ||
                value instanceof Number ||
                value instanceof Boolean) {
                formGroup.addControl(key, new FormControl(value));
            } else {
                formGroup.addControl(key, FormUtils.getFormGroup(value) as AbstractControl);
            }
        });

        return formGroup;
    }

    static getFormArray(items: Array<any>): FormArray {
        const formControls = new Array<AbstractControl>();

        for (const item of items) {
            if (item instanceof Array) {
                formControls.push(FormUtils.getFormArray(item) as AbstractControl);
            } else if (item instanceof String ||
                item instanceof Number ||
                item instanceof Boolean) {
                formControls.push( new FormControl(item));
            } else {
                formControls.push(FormUtils.getFormGroup(item) as AbstractControl);
            }
        }

        return new FormArray(formControls);
    }

}
