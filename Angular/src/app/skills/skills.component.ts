import {Component, Input, OnInit, QueryList, ViewChildren} from '@angular/core';
import {CountToSettings, SkillSlideComponent, SkillSlideSettings} from './skill-slide/skill-slide.component';
import * as $ from 'jquery';
import {NgbSlideEvent} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-skills',
  templateUrl: './skills.component.html',
  styleUrls: ['./skills.component.scss']
})
export class SkillsComponent implements OnInit {

  constructor() {
  }

  @ViewChildren(SkillSlideComponent)
  slides: QueryList<SkillSlideComponent>;

  @Input()
  slideInterval = 5000;

  countToSettingsSprint: SkillSlideSettings;
  countToSettingsArsenal: SkillSlideSettings;

  ngOnInit() {

    this.countToSettingsSprint = {
      name: '3/4 court sprint',
      values: [
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
      ]
    };

    this.countToSettingsArsenal = {
      name: 'Arsenal 40m run',
      values: [
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
      ]
    };
  }


  onSlide($event: NgbSlideEvent) {
    let currentSlide;
    if ($event.current === 'ngb-slide-4') {
      currentSlide = this.slides.find((item, index) => {
        return index === 0;
      });
    } else if ($event.current === 'ngb-slide-5') {
      currentSlide = this.slides.find((item, index) => {
        return index === 1;
      });
    }

    if (currentSlide) {
      currentSlide.startCount();
    }
  }
}
