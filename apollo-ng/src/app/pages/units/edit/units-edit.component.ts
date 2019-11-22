import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, zip} from 'rxjs';
import {map} from 'rxjs/operators';
import {TreeNode} from 'primeng/api';
import {RxjsUtils} from '../../../modules/core/utils/rxjs.utils';
import {MessageService} from '../../../modules/core/services/message.service';
import {UnitService} from '../../../services/rest/unit.service';
import {IUnit, Unit} from '../../../entities/model/unit.model';

@Component({
    selector: 'app-activity-categories-edit',
    templateUrl: './units-edit.component.html',
    styleUrls: ['./units-edit.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class UnitsEditComponent implements OnInit {

    constructor(private unitService: UnitService,
                private activatedRoute: ActivatedRoute,
                private messageService: MessageService,
                private router: Router) {
    }

    unitId: number;
    unit: IUnit;
    unitForm: FormGroup;


    ngOnInit() {
        const params$ = this.activatedRoute.params;
        params$.subscribe((params) => {
            this.unitId = +params.id;
            const getUnit$ = this.getUnit(this.unitId);
            const getCategories$ = this.unitService.query({
                page: 0,
                size: 1000,
            });

            getUnit$.subscribe((unit: IUnit) => {
                this.unit = unit;
                this.setUnitForm(this.unit);
            });

        });
    }

    setUnitForm(unit: IUnit) {

        this.unitForm = new FormGroup({
            id: new FormControl(unit.id),
            name: new FormControl(unit.name, [Validators.required]),
            abbreviation: new FormControl(unit.abbreviation, [Validators.required]),
        });
    }

    saveUnit() {
        if (this.unitForm.valid) {

            const unitToSave = this.unitForm.value as IUnit;
            let saveUnit$;
            if (unitToSave.id) {
                saveUnit$ = this.unitService.update(unitToSave);
            } else {
                saveUnit$ = this.unitService.create(unitToSave);
            }


            saveUnit$.subscribe(
                (unitResponse: HttpResponse<IUnit>) => {
                    this.unit = unitResponse.body;
                    this.setUnitForm(this.unit);
                    this.messageService.showSuccess('Jednotka uložena');
                    this.router.navigate(['/units/list']);
                },
                (errorResponse: HttpErrorResponse) => {
                    this.messageService.showError('Jednota nebyla uložena', errorResponse.error.detail);
                });
        }
    }


    getUnit(unitId: number): Observable<IUnit> {
        if (unitId) {
            return this.unitService
                .find(unitId)
                .pipe(map((unitResponse: HttpResponse<IUnit>) => {
                return unitResponse.body;
            }));

        } else {
            return RxjsUtils.create(new Unit());
        }
    }

}
