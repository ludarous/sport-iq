import {TranslateService} from '@ngx-translate/core';
import {Injectable} from '@angular/core';
import {EnumTranslatorService} from '../modules/shared-components/services/enum-translator.service';
import {ResultType} from '../entities/model/activity-result.model';

@Injectable()
export class EnumTranslatorServiceImpl extends EnumTranslatorService {

    constructor(private translateService: TranslateService) {
        super();
    }

    public translate(enumObj: any): any {

        if (enumObj) {
            if (enumObj instanceof ResultType) {

                if (enumObj.equals(ResultType.LESS_IS_BETTER)) {
                    return this.translateService.instant(`Méně je lépe`);
                } else if (enumObj.equals(ResultType.MORE_IS_BETTER)) {
                    return this.translateService.instant(`Více je lépe`);
                } else {
                    return enumObj;
                }
            }
            return enumObj;
        }

        return enumObj;
    }
}
