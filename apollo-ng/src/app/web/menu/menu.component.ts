import {AfterViewChecked, Component, ElementRef, HostListener, OnInit, ViewChild} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit, AfterViewChecked {

  constructor(private translateService: TranslateService) {

  }

  private _toggleButton: ElementRef;
  @ViewChild('toggleButton', {static: true})
  get toggleButton(): ElementRef<any> {
    return this._toggleButton;
  }

  set toggleButton(value: ElementRef<any>) {
    if (value) {
      this._toggleButton = value;
      this.setToggleNav();
    }
  }

  toggleButtonVisible: boolean;

  ngOnInit() {
  }

  setLang(lang: string) {
    this.translateService.use(lang);
  }

  getSelectedLang(): string {
    return this.translateService.currentLang;
  }

  setToggleNav() {
    const display = getComputedStyle(this.toggleButton.nativeElement).display;
    this.toggleButtonVisible = (display !== 'none');
  }

  ngAfterViewChecked(): void {
    this.setToggleNav();
  }

    login() {
        location.href = environment.backendUrl + '/login';
    }
}
