import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {MessageService} from '../../../modules/core/services/message.service';
import {IWorkout} from '../../../entities/model/workout.model';
import {WorkoutService} from '../../../services/rest/workout.service';


@Component({
    selector: 'app-workouts-list',
    templateUrl: './workouts-list.component.html',
    styleUrls: ['./workouts-list.component.scss']
})
export class WorkoutsListComponent implements OnInit {

    tableCols: Array<any>;
    workouts: Array<IWorkout>;

    constructor(private workoutsService: WorkoutService,
                private messageService: MessageService) {
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

    delete(event, category: IWorkout) {
        event.stopPropagation();

        if (confirm('Opravdu chceš smazat test ' + category.name)) {
            this.workoutsService.remove(category.id).subscribe(() => {
                this.load();
            }, (errorResponse: HttpErrorResponse) => {
                this.messageService.showError('Test se nepodařilo smazat', errorResponse.message);
            });
        }

    }

}
