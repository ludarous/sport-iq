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
    this.durationInMilies = this.duration * 2 * 1000;

    this.steps = Math.ceil(this.durationInMilies / this.refreshInterval);
    this.increment = ((this.CountTo - this.from) / this.steps);
    this.num = this.from;
  }

  tick() {
    setTimeout(() => {
      this.num += this.increment;
      this.step++;
      if (this.step >= this.steps) {
        this.num = this.CountTo;
        this.e.textContent = this.CountTo;
      } else {
        this.e.textContent = (this.num).toFixed(this.digits);
        this.tick();
      }
    }, this.refreshInterval);
  }

  start() {
    this.clear();
    this.calculate();
    this.tick();
  }

  clear() {
    this.step = 0;
    this.steps = undefined;
    this.increment = undefined;
    this.num = undefined;
  }
}
