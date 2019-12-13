import {Component, Input, OnInit} from '@angular/core';
import {AthleteWorkoutSummary} from '../../../../entities/summaries/athlete-workout-summary';

@Component({
  selector: 'app-athlete-workout-summary',
  templateUrl: './athlete-workout-summary.component.html',
  styleUrls: ['./athlete-workout-summary.component.scss']
})
export class AthleteWorkoutSummaryComponent implements OnInit {

  constructor() { }

  @Input()
  workoutSummary: AthleteWorkoutSummary;

  ngOnInit() {
  }

}
