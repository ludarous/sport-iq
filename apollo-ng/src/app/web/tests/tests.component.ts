import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import { TestDetail } from './test-detail/test-detail.component';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-tests',
  templateUrl: './tests.component.html',
  styleUrls: ['./tests.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class TestsComponent implements OnInit {

  constructor(private translateService: TranslateService) { }

  testDetails: Array<TestDetail>;

  ngOnInit() {
      this.testDetails = [
          // {
          //     name: 'tests.versatile-ball-sports-test.title',
          //     sections: [
          //         {
          //             title: 'tests.point-1-title',
          //             content: 'tests.versatile-ball-sports-test.when',
          //         },
          //         {
          //             title: 'tests.point-2-title',
          //             content: 'tests.versatile-ball-sports-test.who',
          //         },
          //         {
          //             title: 'tests.point-3-title',
          //             content: 'tests.versatile-ball-sports-test.what',
          //         },
          //         {
          //             title: 'tests.point-4-title',
          //             content: 'tests.versatile-ball-sports-test.desc',
          //         },
          //         {
          //             title: 'tests.point-5-title',
          //             content: 'tests.versatile-ball-sports-test.rec',
          //         }
          //     ]
          // },
          {
              name: 'tests.versatile-test.title',
              sections: [
                  {
                      title: 'tests.point-1-title',
                      content: 'tests.versatile-test.when',
                  },
                  {
                      title: 'tests.point-2-title',
                      content: 'tests.versatile-test.who',
                  },
                  {
                      title: 'tests.point-3-title',
                      content: 'tests.versatile-test.what',
                  },
                  {
                      title: 'tests.point-4-title',
                      content: 'tests.versatile-test.desc',
                  },
                  {
                      title: 'tests.point-5-title',
                      content: 'tests.versatile-test.rec',
                  }
              ]
          },
          {
              name: 'tests.special-test.title',
              sections: [
                  {
                      title: 'tests.point-1-title',
                      content: 'tests.special-test.when',
                  },
                  {
                      title: 'tests.point-2-title',
                      content: 'tests.special-test.who',
                  },
                  {
                      title: 'tests.point-3-title',
                      content: 'tests.special-test.what',
                  },
                  {
                      title: 'tests.point-4-title',
                      content: 'tests.special-test.desc',
                  },
                  {
                      title: 'tests.point-5-title',
                      content: 'tests.special-test.rec',
                  }
              ]
          }
      ];
  }
}
