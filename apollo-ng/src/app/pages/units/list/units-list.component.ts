import {Component, OnInit} from '@angular/core';
import {TreeNode} from 'primeng/api';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {MessageService} from '../../../modules/core/services/message.service';
import {UnitService} from '../../../services/rest/unit.service';
import {IUnit} from '../../../entities/model/unit.model';

@Component({
    selector: 'app-units-list',
    templateUrl: './units-list.component.html',
    styleUrls: ['./units-list.component.scss']
})
export class UnitsListComponent implements OnInit {

    tableCols: Array<any>;
    units: Array<IUnit>;

    constructor(private unitsService: UnitService,
                private messageService: MessageService) {
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

    delete(event, category: IUnit) {
        event.stopPropagation();

        if (confirm('Opravdu chceš smazat jednotku ' + category.name)) {
            this.unitsService.remove(category.id).subscribe(() => {
                this.load();
            }, (errorResponse: HttpErrorResponse) => {
                this.messageService.showError('Kategorii se nepodařilo smazat', errorResponse.message);
            });
        }

    }

}
