import {Component, Input, OnInit} from '@angular/core';
import {AthleteActivitySummary} from '../../../../entities/summaries/athlete-activity-summary';

@Component({
  selector: 'app-athlete-activity-summary',
  templateUrl: './athlete-activity-summary.component.html',
  styleUrls: ['./athlete-activity-summary.component.scss']
})
export class AthleteActivitySummaryComponent implements OnInit {

  constructor() { }

  @Input()
  activitySummary: AthleteActivitySummary;

  ngOnInit() {
  }

}
