import {AfterContentChecked, AfterViewChecked, Component, OnInit} from '@angular/core';
import * as $ from 'jquery';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.scss']
})
export class AboutComponent implements OnInit, AfterContentChecked {

  constructor() {
  }

  ngOnInit() {

  }

  ngAfterContentChecked(): void {
    this.carouselNormalization();
  }

  carouselNormalization() {
    const items = $('#about-carousel .carousel-item');

    if (items.length) {
      this.normalizeHeights(items);

      $(window).on('resize orientationchange', function() {
        this.normalizeHeights(items);
      });
    }
  }

  normalizeHeights(items: any) {
    if (items) {
      const heights: Array<number> = new Array<number>();
      let tallest: any;

      items.each(function() {
        const itemHeight = ($(this).height()) as number;
        heights.push(itemHeight);
      });
      heights.push($('.slide-wrap').height());

      tallest = Math.max(...heights);

      items.each(function() {
        $(this).css('min-height', tallest + 'px');
        $(this).css('height', tallest + 'px');
      });
    }
  }

}
