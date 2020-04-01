import {Component, Input, OnInit} from '@angular/core';
import {IAthlete} from '../../../../../entities/model/athlete.model';

@Component({
  selector: 'app-athlete-info',
  templateUrl: './athlete-info.component.html',
  styleUrls: ['./athlete-info.component.scss']
})
export class AthleteInfoComponent implements OnInit {

  constructor() { }

  @Input()
  athlete: IAthlete;

  ngOnInit() {
  }

}
