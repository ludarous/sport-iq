import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  constructor(private translateService: TranslateService) {

  }

  ngOnInit() {
  }

  setLang(lang: string) {
    this.translateService.use(lang);
  }

}
