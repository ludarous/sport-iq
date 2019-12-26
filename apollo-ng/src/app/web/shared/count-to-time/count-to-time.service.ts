import { Injectable } from '@angular/core';
import {Observable, Subject} from 'rxjs';

@Injectable()
export class CountToTimeService {

  listeners: any = {};
  events: any;
  state = new Subject();
  progress = 0;
  speed = 200;

  constructor() {

    this.events = this.state as Observable<any>;

    this.events.subscribe(
      ({id, args}) => {
        let ct = 0;
        const fnCheck = () => {
          if (ct > 2) { return; }
          if (!this.listeners[id]) {
            ct++;
            window.setTimeout(fnCheck, 500);
          } else {
            for (const listener of this.listeners[id]) {
              listener(...args);
            }
          }
        }
        fnCheck();
      });
  }

  /** Registers a component with the service **/
  register(id, listener) {
    if (!this.listeners[id]) {
      this.listeners[id] = [];
    }

    this.listeners[id].push(listener);
  }

  /** Start counting **/
  start(id) {
    id = id || 'countto';
    this.state.next({id});
  }
}
