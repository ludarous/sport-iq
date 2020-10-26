import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ToastService} from '../../../../../modules/core/services/message.service';
import {IAthlete} from '../../../../entities/model/athlete.model';
import {AthleteService} from '../../../../services/rest/athlete.service';


@Component({
    selector: 'app-athletes-list',
    templateUrl: './athletes-list.component.html',
    styleUrls: ['./athletes-list.component.scss']
})
export class AthletesListComponent implements OnInit {

    tableCols: Array<any>;
    athletes: Array<IAthlete>;

    constructor(private athletesService: AthleteService,
                private toastService: ToastService) {
    }

    ngOnInit() {

        this.tableCols = [
            {field: 'lastName', header: 'Jméno', map: (v: IAthlete, c) => v.firstName + ' ' + v.lastName},
            {field: 'email', header: 'E-mail'},
            {field: 'birthDate', header: 'Datum narození', map: (v: IAthlete, c) => v.birthDate ? v.birthDate.toLocaleDateString() : ''},
        ];

        this.load();

    }

    load() {
        const getAthletes$ = this.athletesService.query({
            page: 0,
            size: 1000,
        });

        getAthletes$.subscribe((athletes: HttpResponse<Array<IAthlete>>) => {
            this.athletes = athletes.body;
        });
    }

    delete(event, athlete: IAthlete) {
        event.stopPropagation();
        if (confirm('Opravdu chceš smazat sportovce ' + athlete.firstName + ' ' + athlete.lastName)) {
            this.athletesService.remove(athlete.id).subscribe(() => {
                this.load();
            }, (errorResponse: HttpErrorResponse) => {
                this.toastService.showError('Sportovce se nepodařilo smazat', errorResponse.message);
            });
        }

    }

}
