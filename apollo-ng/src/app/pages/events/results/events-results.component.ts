import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {EventResultsService} from './events-results.service';
import {IAthlete} from '../../../entities/model/athlete.model';
import {IEvent} from '../../../entities/model/event.model';
import {IWorkout} from '../../../entities/model/workout.model';
import {IActivity} from '../../../entities/model/activity.model';
import {IAthleteWorkout} from '../../../entities/model/athlete-workout.model';
import {IAthleteActivity} from '../../../entities/model/athlete-activity.model';

@Component({
    selector: 'app-events-results',
    templateUrl: './events-results.component.html',
    styleUrls: ['./events-results.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EventsResultsComponent implements OnInit {

    constructor(private eventResultsService: EventResultsService,
                private activatedRoute: ActivatedRoute) {
    }

    get event(): IEvent {
        return this.eventResultsService.event;
    }

    get selectedAthlete(): IAthlete {
        return this.eventResultsService.selectedAthlete;
    }

    get selectedAthleteEvent(): IAthlete {
        return this.eventResultsService.selectedAthleteEvent;
    }

    get selectedWorkout(): IWorkout {
        return this.eventResultsService.selectedWorkout;
    }

    get selectedAthleteWorkout(): IAthleteWorkout {
        return this.eventResultsService.selectedAthleteWorkout;
    }

    get selectedActivity(): IActivity {
        return this.eventResultsService.selectedActivity;
    }

    get selectedAthleteActivity(): IAthleteActivity {
        return this.eventResultsService.selectedAthleteActivity;
    }



    ngOnInit() {

        const params$ = this.activatedRoute.params;
        params$.subscribe((params) => {
            this.eventResultsService.init(+params.id,  +params.athleteId, +params.workoutId, +params.activityId)
        });
    }

    selectAthlete(athlete: IAthlete) {
        this.eventResultsService.selectAthlete(athlete);
    }

    selectWorkout(workout: IWorkout) {
        this.eventResultsService.selectWorkout(workout);
    }

    selectActivity(activity: IActivity) {
        this.eventResultsService.selectActivity(activity);
    }


    showAthleteEventForm(): boolean {
        return this.eventResultsService.showAthleteEventForm();
    }

    showAthleteWorkoutForm(): boolean {
        return this.eventResultsService.showAthleteWorkoutForm();
    }

    showAthleteActivityForm(): boolean {
        return this.eventResultsService.showAthleteActivityForm();
    }
}
