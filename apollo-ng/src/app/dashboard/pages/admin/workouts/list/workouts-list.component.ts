import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ToastService} from '../../../../../modules/core/services/message.service';
import {IWorkout} from '../../../../entities/model/workout.model';
import {WorkoutService} from '../../../../services/rest/workout.service';


@Component({
    selector: 'app-workouts-list',
    templateUrl: './workouts-list.component.html',
    styleUrls: ['./workouts-list.component.scss']
})
export class WorkoutsListComponent implements OnInit {

    tableCols: Array<any>;
    workouts: Array<IWorkout>;

    constructor(private workoutsService: WorkoutService,
                private toastService: ToastService) {
    }

    ngOnInit() {

        this.tableCols = [
            {field: 'name', header: 'Název'},
            {field: 'description', header: 'Popis'},
        ];

        this.load();

    }

    load() {
        const getWorkouts$ = this.workoutsService.query({
            page: 0,
            size: 1000,
        });

        getWorkouts$.subscribe((workouts: HttpResponse<Array<IWorkout>>) => {
            this.workouts = workouts.body;
        });
    }

    delete(event, workout: IWorkout) {
        event.stopPropagation();

        if (confirm('Opravdu chceš smazat test ' + workout.name)) {
            this.workoutsService.remove(workout.id).subscribe(() => {
                this.load();
            }, (errorResponse: HttpErrorResponse) => {
                this.toastService.showError('Test se nepodařilo smazat', errorResponse.message);
            });
        }

    }

}
