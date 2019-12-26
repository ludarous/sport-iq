import { Directive, OnChanges, ElementRef, Input, OnInit } from '@angular/core';

@Directive({
  selector: '[CountTo]'
})
export class CountToDirective implements OnChanges, OnInit {
  @Input()
  CountTo: number;
  @Input()
  from = 0;
  @Input()
  duration = 4;
  @Input()
  digits = 2;

  public e = this.el.nativeElement;
  num: number;
  refreshInterval = 30;
  steps: number;
  step = 0;
  increment: number;
  durationInMilies: number;
  interval: any;

  constructor(private el: ElementRef) {

  }

  ngOnInit() {

  }

  ngOnChanges() {
    if (this.CountTo) {
      this.start();
    }
  }

  calculate() {
    this.durationInMilies = this.duration * 1000;

    this.steps = Math.ceil(this.durationInMilies / this.refreshInterval);
    this.increment = ((this.CountTo - this.from) / this.steps);
    this.num = this.from;
  }

  tick() {
    this.interval = setInterval(() => {
      this.num += this.increment;
      this.step++;
      if (this.step >= this.steps) {
        this.num = this.CountTo;
        this.e.textContent = this.CountTo;
        clearInterval(this.interval);
      } else {
        this.e.textContent = (this.num).toFixed(this.digits);
      }
    }, this.refreshInterval);
  }


  start() {
    this.stop();
    this.calculate();
    this.tick();
  }

  stop() {
    clearInterval(this.interval);
    this.step = 0;
    this.steps = undefined;
    this.increment = undefined;
    this.num = undefined;
  }
}
