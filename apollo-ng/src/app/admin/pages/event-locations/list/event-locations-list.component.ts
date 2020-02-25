import {Component, OnInit} from '@angular/core';
import {TreeNode} from 'primeng/api';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {ToastService} from '../../../../modules/core/services/message.service';
import {Router} from '@angular/router';
import { IEventLocation } from '../../../entities/model/event-location.model';
import { EventLocationService } from '../../../services/rest/event-location.service';

@Component({
    selector: 'app-event-locations-list',
    templateUrl: './event-locations-list.component.html',
    styleUrls: ['./event-locations-list.component.scss']
})
export class EventLocationsListComponent implements OnInit {

    tableCols: Array<any>;
    eventLocations: Array<IEventLocation>;

    constructor(private eventLocationsService: EventLocationService,
                private toastService: ToastService) {
    }

    ngOnInit() {

        this.tableCols = [
            {field: 'name', header: 'Název'},
            {field: 'state', header: 'Stát'},
            {field: 'city', header: 'Město'},
            {field: 'street', header: 'Ulice'},
            {field: 'streetNumber', header: 'Číslo ulice'},
            {field: 'latitude', header: 'Zeměpisná šířka'},
            {field: 'longitude', header: 'Zeměpisná délka'},
        ];

        this.load();

    }

    load() {
        const getEventLocations$ = this.eventLocationsService.query({
            page: 0,
            size: 1000,
        });

        getEventLocations$.subscribe((eventLocations: HttpResponse<Array<IEventLocation>>) => {
            this.eventLocations = eventLocations.body;
        });
    }

    delete(event, eventLocation: IEventLocation) {
        event.stopPropagation();

        if (confirm('Opravdu chceš smazat místo události ' + eventLocation.name)) {
            this.eventLocationsService.remove(eventLocation.id).subscribe(() => {
                this.load();
            }, (errorResponse: HttpErrorResponse) => {
                this.toastService.showError('Místo události se nepodařilo smazat', errorResponse.message);
            });
        }

    }

}
