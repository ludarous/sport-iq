import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAthleteWorkout } from 'app/shared/model/athlete-workout.model';

@Component({
    selector: 'jhi-athlete-workout-detail',
    templateUrl: './athlete-workout-detail.component.html'
})
export class AthleteWorkoutDetailComponent implements OnInit {
    athleteWorkout: IAthleteWorkout;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ athleteWorkout }) => {
            this.athleteWorkout = athleteWorkout;
        });
    }

    previousState() {
        window.history.back();
    }
}
