import {AfterViewChecked, Component, ElementRef, HostListener, OnInit, ViewChild} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import { environment } from '../../../environments/environment';
import { AuthUtils } from '../../modules/core/utils/auth.utils';
import { IUser } from '../../modules/entities/user';
import { AuthService } from '../../modules/auth/services/auth.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit, AfterViewChecked {

  constructor(private translateService: TranslateService,
              private authService: AuthService) {

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
  currentUser: IUser;

  get userFullName(): string {
      if (this.currentUser) {
          return  this.currentUser.firstName + ' ' + this.currentUser.lastName;
      }
  }

  ngOnInit() {
      AuthUtils.userLoggedIn$.subscribe((user: IUser) => {
        this.setUser(user);
      });

      AuthUtils.userLoggedOut$.subscribe((user: IUser) => {
          this.setUser(user);
      });

      AuthUtils.userChanged$.subscribe((user: IUser) => {
          this.setUser(user);
      });

      AuthUtils.userUpdated$.subscribe((user: IUser) => {
          this.setUser(user);
      });
  }

  setLang(lang: string) {
    this.translateService.use(lang);
  }

  setUser(user: IUser) {
      this.currentUser = user;
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
        this.authService.login();
    }
}
