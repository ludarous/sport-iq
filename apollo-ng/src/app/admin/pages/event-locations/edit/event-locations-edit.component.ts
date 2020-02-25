import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';
import {Observable, zip} from 'rxjs';
import {map} from 'rxjs/operators';
import {TreeNode} from 'primeng/api';
import {RxjsUtils} from '../../../../modules/core/utils/rxjs.utils';
import {ToastService} from '../../../../modules/core/services/message.service';
import { EventLocationService } from '../../../services/rest/event-location.service';
import { EventLocation, IEventLocation } from '../../../entities/model/event-location.model';

@Component({
    selector: 'app-activity-categories-edit',
    templateUrl: './event-locations-edit.component.html',
    styleUrls: ['./event-locations-edit.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class EventLocationsEditComponent implements OnInit {

    constructor(private eventLocationService: EventLocationService,
                private activatedRoute: ActivatedRoute,
                private toastService: ToastService,
                private router: Router) {
    }

    eventLocationId: number;
    eventLocation: IEventLocation;
    eventLocationForm: FormGroup;


    ngOnInit() {
        const params$ = this.activatedRoute.params;
        params$.subscribe((params) => {
            this.eventLocationId = +params.id;
            const getEventLocation$ = this.getEventLocation(this.eventLocationId);
            getEventLocation$.subscribe((eventLocation: IEventLocation) => {
                this.eventLocation = eventLocation;
                this.setEventLocationForm(this.eventLocation);
            });

        });
    }

    setEventLocationForm(eventLocation: IEventLocation) {

        this.eventLocationForm = new FormGroup({
            id: new FormControl(eventLocation.id),
            name: new FormControl(eventLocation.name, [Validators.required]),
            state: new FormControl(eventLocation.state),
            city: new FormControl(eventLocation.city),
            street: new FormControl(eventLocation.street),
            streetNumber: new FormControl(eventLocation.streetNumber),
            latitude: new FormControl(eventLocation.latitude),
            longitude: new FormControl(eventLocation.longitude),
        });
    }

    saveEventLocation() {
        if (this.eventLocationForm.valid) {

            const eventLocationToSave = this.eventLocationForm.value as IEventLocation;
            let saveEventLocation$;
            if (eventLocationToSave.id) {
                saveEventLocation$ = this.eventLocationService.update(eventLocationToSave);
            } else {
                saveEventLocation$ = this.eventLocationService.create(eventLocationToSave);
            }


            saveEventLocation$.subscribe(
                (eventLocationResponse: HttpResponse<IEventLocation>) => {
                    this.eventLocation = eventLocationResponse.body;
                    this.setEventLocationForm(this.eventLocation);
                    this.toastService.showSuccess('Místo události uloženo');
                    this.router.navigate(['/admin/event-locations/list']);
                },
                (errorResponse: HttpErrorResponse) => {
                    this.toastService.showError('Místo události nebylo uloženo', errorResponse.error.detail);
                });
        }
    }


    getEventLocation(eventLocationId: number): Observable<IEventLocation> {
        if (eventLocationId) {
            return this.eventLocationService
                .find(eventLocationId)
                .pipe(map((eventLocationResponse: HttpResponse<IEventLocation>) => {
                return eventLocationResponse.body;
            }));

        } else {
            return RxjsUtils.create(new EventLocation());
        }
    }

}
