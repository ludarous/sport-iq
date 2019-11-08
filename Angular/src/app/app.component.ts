import {Component, OnInit} from '@angular/core';
import * as $ from 'jquery';
import 'jquery.easing';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'Angular';

  ngOnInit(): void {
    $('#status').delay(100).fadeOut('slow');
    $('#preloader').delay(500).fadeOut('slow');
    $('body').delay(500).css({overflow : 'visible'});
  }
}


