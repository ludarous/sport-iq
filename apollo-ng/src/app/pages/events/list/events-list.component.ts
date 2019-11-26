import {Component, OnInit} from '@angular/core';
import {HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {MessageService} from '../../../modules/core/services/message.service';
import {IEvent} from '../../../entities/model/event.model';
import {EventService} from '../../../services/rest/event.service';

@Component({
    selector: 'app-events-list',
    templateUrl: './events-list.component.html',
    styleUrls: ['./events-list.component.scss']
})
export class EventsListComponent implements OnInit {

    tableCols: Array<any>;
    events: Array<IEvent>;

    constructor(private eventsService: EventService,
                private messageService: MessageService) {
    }

    ngOnInit() {

        this.tableCols = [
            {field: 'name', header: 'Název'},
            {field: 'date', header: 'Datum konání', map: (v: IEvent, c) => v.date ? v.date.format('DD.MM.YYYY') : ''},
            {header: 'Počet účastníků', map: (v: IEvent, c) => v.athletes ? v.athletes.length : ''},
        ];

        this.load();

    }

    load() {
        const getEvents$ = this.eventsService.query({
            page: 0,
            size: 1000,
        });

        getEvents$.subscribe((events: HttpResponse<Array<IEvent>>) => {
            this.events = events.body;
        });
    }

    delete(e, event: IEvent) {
        e.stopPropagation();
        if (confirm('Opravdu chceš smazat událost ' + event.name)) {
            this.eventsService.remove(event.id).subscribe(() => {
                this.load();
            }, (errorResponse: HttpErrorResponse) => {
                this.messageService.showError('Událost se nepodařilo smazat', errorResponse.message);
            });
        }

    }

}
