import {Component, Input, Output, EventEmitter, AfterContentInit, ElementRef, OnDestroy} from '@angular/core';
import {CountToTimeService} from './count-to-time.service';
import {timer} from 'rxjs';
import {finalize, map, takeUntil} from 'rxjs/operators';

@Component({
  selector: 'ngx-count-to-time',
  template: `{{output}}`
})
export class CountToTimeComponent implements OnDestroy, AfterContentInit {

  @Input() id = 'countto';
  @Input() from = 0;
  @Input() to: number;
  @Input() decimals = 0;
  @Input() duration = 5000;

  @Output() finish: EventEmitter<any>;

  state: any;
  progress: number;
  output: any;
  speed = 50;

  constructor(private countToTimeService: CountToTimeService,
              private elementRef: ElementRef) {
    // this.state = counttoService.state;
  }

  ngAfterContentInit() {
    // this.from = this.elementRef.nativeElement.innerHTML || 0;
    this.progress = this.from;
    this.output = this.from;

    const ranges = [
      {range: 'trillions', postfix: 'T', count: this.to >= 1000000000000000 ? 999 : Math.floor(this.to / 1000000000000)},
      {range: 'billions', postfix: 'B', count: this.to >= 1000000000000 ? 999 : Math.floor(this.to / 1000000000)},
      {range: 'millions', postfix: 'M', count: this.to >= 1000000000 ? 999 : Math.floor(this.to / 1000000)},
      {range: 'thousands', postfix: 'K', count: this.to >= 1000000 ? 999 : Math.floor(this.to / 1000)},
      {range: 'digits', postfix: '', count: this.to >= 1000 ? 999 : this.to},
      // { range: 'hundreds',  postfix: '',  count: this.to >= 1000 ? 900 : this.to - 99 },
      // { range: 'digits',    postfix: '',  count: this.to >= 100 ? 99 : this.to },
    ];
    const output = ranges.reduce((acc, curr) => {
      acc.total += curr.count;
      const steps = curr.count > 500 ? 10
        : curr.count > 100 ? 5
          : 1;
      const count = Math.floor(curr.count / steps);
      const speed = count > 500 ? 5
        : count > 100 ? 10
          : count > 50 ? 50
            : 100;
      const fn = acc.next;
      acc.next = curr.count > 0 ? () => this.start(count, speed, steps, curr.postfix, fn) : null;
      return acc;
    }, {total: 0, next: null});
    // console.log('output', output);
    // this.speed = Math.floor(this.duration / output.total);

    this.countToTimeService.register(this.id, () => {
      output.next();
    });
  }

  ngOnDestroy() {
    // this.counttoService.state.unsubscribe();
  }

  private start(take, speed, steps = 1, postfix = '', next = null, delay = 0) {
    // console.log('take-speed-steps-postfix', take, speed, steps, postfix);
    timer(delay, speed)
      .pipe(takeUntil(take + 1))
      .pipe(map((val) => {
        this.output = `${val * steps} ${postfix}`;
      }));
  }

  isStarted(): boolean {
    return this.progress > 0 && this.progress < this.to;
  }
}
