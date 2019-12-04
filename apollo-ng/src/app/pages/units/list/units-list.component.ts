import {Component, OnInit} from '@angular/core';
import {TreeNode} from 'primeng/api';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ToastService} from '../../../modules/core/services/message.service';
import {UnitService} from '../../../services/rest/unit.service';
import {IUnit} from '../../../entities/model/unit.model';
import {Router} from '@angular/router';

@Component({
    selector: 'app-units-list',
    templateUrl: './units-list.component.html',
    styleUrls: ['./units-list.component.scss']
})
export class UnitsListComponent implements OnInit {

    tableCols: Array<any>;
    units: Array<IUnit>;

    constructor(private unitsService: UnitService,
                private ToastService: ToastService) {
    }

    ngOnInit() {

        this.tableCols = [
            {field: 'name', header: 'Název'},
            {field: 'abbreviation', header: 'Zkratka'},
        ];

        this.load();

    }

    load() {
        const getUnits$ = this.unitsService.query({
            page: 0,
            size: 1000,
        });

        getUnits$.subscribe((units: HttpResponse<Array<IUnit>>) => {
            this.units = units.body;
        });
    }

    delete(event, unit: IUnit) {
        event.stopPropagation();

        if (confirm('Opravdu chceš smazat jednotku ' + unit.name)) {
            this.unitsService.remove(unit.id).subscribe(() => {
                this.load();
            }, (errorResponse: HttpErrorResponse) => {
                this.ToastService.showError('Kategorii se nepodařilo smazat', errorResponse.message);
            });
        }

    }

}
