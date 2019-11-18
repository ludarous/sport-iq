import { Component } from '@angular/core';
import { AppComponent } from './app.component';
import {AuthService} from './modules/auth/services/auth.service';
import {IUser} from './modules/entities/user';

@Component({
    selector: 'app-topbar',
    templateUrl: './app.topbar.component.html'
})
export class AppTopBarComponent {

    constructor(public app: AppComponent,
                private authService: AuthService) {

    }

    get isLoggeedIn(): boolean {
        return !!this.currentUser;
    }

    get currentUser(): IUser {
        return this.authService.getCurrentUser();
    }

    themeChange(e) {
        const themeLink: HTMLLinkElement = document.getElementById('theme-css') as HTMLLinkElement;
        const href = themeLink.href;
        const themeFile = href.substring(href.lastIndexOf('/') + 1, href.lastIndexOf('.'));
        const themeTokens = themeFile.split('-');
        const themeName = themeTokens[1];
        const themeMode = themeTokens[2];
        const newThemeMode = (themeMode === 'dark') ? 'light' : 'dark';

        this.app.changeTheme(themeName + '-' + newThemeMode);
    }

    login() {
        this.authService.login();
    }
}
