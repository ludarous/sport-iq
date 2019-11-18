import {Injectable} from '@angular/core';
import {Message} from 'primeng/primeng';
import {TranslateService} from '@ngx-translate/core';


/**
 * MessageService for the global Growl messages.
 */
@Injectable()
export class MessageService {

  public messages: Message[] = [];
  public criticalMessages: Message[] = [];

  private serverErrorMessageBeginValue = 'Je nám líto, ale nastala chyba na serveru. Kontaktujte, prosím, technickou podporu na';

  constructor(private translateService: TranslateService) {

  }


  public showMessage(summary: string, detail: string, severity: string, critical: boolean = false): void {
    const msg: Message = {summary: summary, detail: detail, severity: severity};
    if (critical) this.criticalMessages = [...this.criticalMessages, msg];
    else this.messages = [...this.messages, msg];
  }

  public cleanMessages(): void {
    this.messages = [];
    this.criticalMessages = [];
  }

  public showLoadError(error: any): void {
    this.showError('Problém s načtením dat', 'Načtení dat se nezdařilo, detaily: ' + error);
  }

  public showError(summary: string, detail: string, critical: boolean = true): void {
    this.showMessage(summary, detail, 'error', critical);
  }

  public showWarn(summary: string, detail: string, critical: boolean = false): void {
    this.showMessage(summary, detail, 'warn', critical);
  }

  public showInfo(summary: string, detail: string, critical: boolean = false): void {
    this.showMessage(summary, detail, 'info', critical);
  }

  public showSuccess(summary: string, detail: string, critical: boolean = false): void {
    this.showMessage(summary, detail, 'success', critical);
  }

}
