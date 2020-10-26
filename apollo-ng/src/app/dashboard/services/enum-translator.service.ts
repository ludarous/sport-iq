import {TranslateService} from '@ngx-translate/core';
import {Injectable} from '@angular/core';
import {EnumTranslatorService} from '../../modules/shared-components/services/enum-translator.service';
import {ResultType} from '../entities/model/activity-result.model';
import {Sex} from '../entities/model/athlete.model';
import { Laterality } from '../entities/model/enums/laterality.enum';

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
            } else if (enumObj instanceof Sex) {

                if (enumObj.equals(Sex.MALE)) {
                    return this.translateService.instant(`Muž`);
                } else if (enumObj.equals(Sex.FEMALE)) {
                    return this.translateService.instant(`Žena`);
                } else if (enumObj.equals(Sex.OTHER)) {
                    return this.translateService.instant(`Jiné`);
                } else {
                    return enumObj;
                }
            } else if (enumObj instanceof Laterality) {

                if (enumObj.equals(Laterality.RIGHT)) {
                    return this.translateService.instant(`Pravá`);
                } else if (enumObj.equals(Laterality.LEFT)) {
                    return this.translateService.instant(`Levá`);
                } else {
                    return enumObj;
                }
            }
            return enumObj;
        }

        return enumObj;
    }
}
