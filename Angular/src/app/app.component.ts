import {Component, OnInit} from '@angular/core';
import * as $ from 'jquery';
import 'jquery.easing';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  constructor(translate: TranslateService) {
    translate.setDefaultLang('en');
    translate.use('en');
  }


  title = 'Angular';



  ngOnInit(): void {
    $('#status').delay(100).fadeOut('slow');
    $('#preloader').delay(500).fadeOut('slow');
    $('body').delay(500).css({overflow : 'visible'});

    /* Scroll Up */
    $(window).scroll(function() {
      if ($(this).scrollTop() > 100) {
        $('.scroll-up').fadeIn();
      } else {
        $('.scroll-up').fadeOut();
      }
    });

    $('.scroll-up').click(function() {
      $('html, body').animate({
        scrollTop: 0
      }, 600);
      return false;
    });
  }
}


