import { Component, Input, OnInit, QueryList, ViewChildren } from '@angular/core';
import { CountToSettings, SkillSlideComponent, SkillSlideSettings } from './skill-slide/skill-slide.component';
import * as $ from 'jquery';
import { NgbSlideEvent } from '@ng-bootstrap/ng-bootstrap';

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
                    time: 3.28,
                    name: 'Stephen Curry',
                    club: 'Golden State Warriors'
                },
                {
                    time: 3.45,
                    name: 'Kevin Durant',
                    club: 'Brooklyn Nets'
                },
                {
                    time: 3.55,
                    name: 'DeMarcus Cousins',
                    club: 'Los Angeles Lakers'
                },
                {
                    time: 3.28,
                    name: 'Blake Griffin',
                    club: 'Detroit Pistons'
                }
            ]
        };

        this.countToSettingsArsenal = {
            name: 'Lane agility',
            values: [
                {
                    time: 11.07,
                    name: 'Stephen Curry',
                    club: 'Golden State Warriors'
                },
                {
                    time: 12.33,
                    name: 'Kevin Durant',
                    club: 'Brooklyn Nets'
                },
                {
                    time: 11.40,
                    name: 'DeMarcus Cousins',
                    club: 'Los Angeles Lakers'
                },
                {
                    time: 10.95,
                    name: 'Blake Griffin',
                    club: 'Detroit Pistons'
                }
            ]
        };
    }


    onSlide($event: NgbSlideEvent) {
        let currentSlide;

        this.slides.map(s => s.stopCount());

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
