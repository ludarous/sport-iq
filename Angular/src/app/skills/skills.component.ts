import {Component, Input, OnInit, ViewChildren} from '@angular/core';
import {CountToSettings, SkillSlideComponent} from './skill-slide/skill-slide.component';
import * as $ from 'jquery';

@Component({
  selector: 'app-skills',
  templateUrl: './skills.component.html',
  styleUrls: ['./skills.component.scss']
})
export class SkillsComponent implements OnInit {

  constructor() { }

  @ViewChildren('app-skill-slide')
  slides: Array<SkillSlideComponent>;

  @Input()
  slideInterval = 5000;

  countToSettingsSprint: Array<CountToSettings> = new Array<CountToSettings>();
  countToSettingsArsenal: Array<CountToSettings> = new Array<CountToSettings>();

  ngOnInit() {

    setTimeout(() => {
      const $carousel = $('#skills-carousel');
      const slides = this.slides;
      $carousel.on('slide.bs.carousel', function() {
        alert('asdasd')
        for (const slide of slides) {
          slide.startCount();
        }
      });
    }, 2000);

    this.countToSettingsSprint = [
      {
        time: 3.04,
        name: 'Jared Harper',
        club: 'Boston Celtics'
      },
      {
        time: 3.08,
        name: 'Loonie Walker',
        club: 'San Antonio Spurs'
      },
      {
        time: 3.16,
        name: 'Kemba Walker',
        club: 'Boston Celtics'
      },
      {
        time: 0,
        name: 'TY'
      }
    ];

    this.countToSettingsArsenal = [
      {
        time: 4.82,
        name: 'Thiery Henry',
        club: 'Arsenal'
      },
      {
        time: 4.78,
        name: 'Theo Walcott',
        club: 'Arsenal'
      },
      {
        time: 4.41,
        name: 'Hector Bellerin',
        club: 'Arsenal'
      },
      {
        time: 0,
        name: 'TY'
      }
    ];
  }



}
