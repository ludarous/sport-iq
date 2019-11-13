import { Component, OnInit } from '@angular/core';
declare const WOW: any;

@Component({
  selector: 'app-technologies',
  templateUrl: './technologies.component.html',
  styleUrls: ['./technologies.component.scss']
})
export class TechnologiesComponent implements OnInit {

  constructor() { }

  ngOnInit() {
    // const wow = new WOW(
    //   {
    //     boxClass:     'wow',      // animated element css class (default is wow)
    //     animateClass: 'animated', // animation css class (default is animated)
    //     offset:       0,          // distance to the element when triggering the animation (default is 0)
    //     mobile:       false       // trigger animations on mobile devices (true is default)
    //   }
    // );
    // wow.init();
  }

}
