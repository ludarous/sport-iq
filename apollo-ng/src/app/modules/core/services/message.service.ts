import {Injectable} from '@angular/core';
import { Message, MessageService } from 'primeng/primeng';
import {TranslateService} from '@ngx-translate/core';


/**
 * ToastService for the global Growl messages.
 */
@Injectable()
export class ToastService {

  private serverErrorMessageBeginValue = 'Je nám líto, ale nastala chyba na serveru. Kontaktujte, prosím, technickou podporu na';

  constructor(private translateService: TranslateService,
              private messageService: MessageService) {

  }


  public showToast(summary: string, detail: string, severity: string): void {
    const msg: Message = {key: 'global', summary, detail, severity};
    this.messageService.add(msg);
  }

  public showLoadError(error: any): void {
    this.showError('Problém s načtením dat', 'Načtení dat se nezdařilo, detaily: ' + error);
  }

  public showError(summary: string, detail?: string): void {
    this.showToast(summary, detail, 'error');
  }

  public showWarn(summary: string, detail?: string): void {
    this.showToast(summary, detail, 'warn');
  }

  public showInfo(summary: string, detail?: string): void {
    this.showToast(summary, detail, 'info');
  }

  public showSuccess(summary: string, detail?: string): void {
    this.showToast(summary, detail, 'success');
  }

}
