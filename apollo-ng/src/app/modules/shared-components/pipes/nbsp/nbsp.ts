import {Pipe, PipeTransform} from '@angular/core';
import {DomSanitizer} from '@angular/platform-browser';

@Pipe({name: 'nbsp'})
export class NbspPipe implements PipeTransform {

  constructor(private sanitized: DomSanitizer) {

  }

  transform(value: any): any {
    let stringValue = value.toString();
    stringValue = stringValue.replace(/ /g, '&nbsp;');
    return this.sanitized.bypassSecurityTrustHtml(stringValue);
  }

}
