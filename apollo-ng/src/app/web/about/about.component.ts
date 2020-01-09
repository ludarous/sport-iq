import {
    AfterViewChecked,
    AfterViewInit,
    Component,
    ElementRef, OnDestroy,
    OnInit,
    ViewChild
} from '@angular/core';
import * as $ from 'jquery';
import { TranslateService } from '@ngx-translate/core';
import ResizeObserver from 'resize-observer-polyfill';

@Component({
    selector: 'app-about',
    templateUrl: './about.component.html',
    styleUrls: ['./about.component.scss']
})
export class AboutComponent implements OnInit, OnDestroy, AfterViewChecked {

    constructor() {
        this.resizeObserver = new ResizeObserver((entries, observer) => {
            this.carouselNormalization();
        });
    }

    resizeObserver: ResizeObserver;


    private _aboutCarousel: ElementRef;
    @ViewChild('aboutCarousel', {static: true})
    get aboutCarousel(): ElementRef<any> {
        return this._aboutCarousel;
    }

    set aboutCarousel(value: ElementRef<any>) {
        if (value) {
            this._aboutCarousel = value;
        }
    }

    ngOnInit() {

    }

    ngOnDestroy(): void {
        this.resizeObserver.disconnect();
    }

    ngAfterViewChecked(): void {
        const items = $('#about-carousel .carousel-item');
        items.each((index, element) => {
            this.resizeObserver.observe(element);
        });
    }

    carouselNormalization() {
        const items = $('#about-carousel .carousel-item');

        if (items.length) {
            this.normalizeHeights(items);
        }
    }

    normalizeHeights(items: any) {
        if (items) {
            const heights: Array<number> = new Array<number>();
            let tallest: any;

            items.each(function () {
                const itemHeight = ($(this).height()) as number;
                heights.push(itemHeight);
            });
            heights.push($('.slide-wrap').height());

            tallest = Math.max(...heights);

            items.each(function () {
                $(this).css('min-height', tallest + 'px');
                setTimeout(() => {
                    $(this).css('height', tallest + 'px');
                }, 500);
            });

        }
    }



}
