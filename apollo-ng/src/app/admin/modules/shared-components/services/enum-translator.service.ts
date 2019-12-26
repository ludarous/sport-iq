import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export abstract class EnumTranslatorService {
  abstract translate(enumObj: any): any;
}
