import {Component, Input, OnInit, ViewChild, ViewChildren} from '@angular/core';
import * as $ from 'jquery';
import {element} from 'protractor';
import {CountToDirective} from '../../shared/directives/count-to.directive';
import {DomSanitizer} from '@angular/platform-browser';

declare const Waypoint: any;

export interface CountToSettings {
  time?: number;
  name?: string;
  club?: string;
}

@Component({
  selector: 'app-skill-slide',
  templateUrl: './skill-slide.component.html',
  styleUrls: ['./skill-slide.component.scss']
})
export class SkillSlideComponent implements OnInit {

  constructor(private sanitizer: DomSanitizer) {
  }

  @Input()
  backgroundUrl: string;

  @Input()
  countToSettings: Array<CountToSettings>;

  sanitizedBackgroundImage: any;

  _countToElements: Array<CountToDirective>;
  @ViewChildren(CountToDirective)
  set countToElements(directives: Array<CountToDirective>) {
    this._countToElements = directives;
  }

  ngOnInit() {

    const backgroundUrl = this.backgroundUrl;
    this.sanitizedBackgroundImage = this.sanitizer.bypassSecurityTrustStyle(`url('${backgroundUrl}')`);

    this.run();
  }

  startCount() {
    for (const countToElement of this._countToElements) {
      countToElement.start();
    }
  }

  run() {
    setTimeout(() => {
      for (const countToElement of this._countToElements) {
        const waypoint = new Waypoint({
          element: countToElement.e,
          handler(direction) {

            countToElement.start();
          },
          offset: 'bottom-in-view'
        });
      }
    }, 0);
  }

}
