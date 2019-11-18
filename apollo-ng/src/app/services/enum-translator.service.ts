import {TranslateService} from '@ngx-translate/core';
import {Injectable} from '@angular/core';
import {EnumTranslatorService} from '../modules/shared-components/services/enum-translator.service';

@Injectable()
export class EnumTranslatorServiceImpl extends EnumTranslatorService {

    constructor(private translateService: TranslateService) {
        super();
    }

    public translate(enumObj: any): any {

        if (enumObj) {
            return enumObj;
        }

        return enumObj;
    }
}
