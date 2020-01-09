import { Component, Input, OnInit } from '@angular/core';

export interface TestSection {
    title: string;
    content: string;
}

export interface TestDetail {
    name: string;
    sections: Array<TestSection>;
}

@Component({
  selector: 'app-test-detail',
  templateUrl: './test-detail.component.html',
  styleUrls: ['./test-detail.component.scss']
})
export class TestDetailComponent implements OnInit {

  constructor() { }

  @Input()
  testDetail: TestDetail;

  ngOnInit() {

  }

}
