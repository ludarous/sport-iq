import {Component, Input, OnInit} from '@angular/core';
import {AthleteActivityResultSummary} from '../../../../entities/summaries/athlete-activity-result-summary';
import {StatsTableItem} from '../../../../entities/summaries/stats';

@Component({
  selector: 'app-athlete-activity-result-summary',
  templateUrl: './athlete-activity-result-summary.component.html',
  styleUrls: ['./athlete-activity-result-summary.component.scss']
})
export class AthleteActivityResultSummaryComponent implements OnInit {

  constructor() { }

  @Input()
  activityResultSummary: AthleteActivityResultSummary;

  statsTableItems: Array<StatsTableItem>;

  get unit(): string {
      return this.activityResultSummary.activityResult.resultUnit.abbreviation;
  }

  ngOnInit() {
      this.statsTableItems = StatsTableItem.createStatsTableItems(
          this.activityResultSummary.athleteActivityResult.value,
          this.activityResultSummary.athleteActivityResult.compareValue,
          this.activityResultSummary.stats);

  }


}

