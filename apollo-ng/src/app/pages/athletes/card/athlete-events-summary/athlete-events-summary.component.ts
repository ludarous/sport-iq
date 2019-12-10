import {Component, Input, OnInit} from '@angular/core';
import {IAthlete} from '../../../../entities/model/athlete.model';
import {IEvent, Event} from '../../../../entities/model/event.model';
import {DropdownItem, SelectItem} from 'primeng/primeng';
import {AthleteEventService} from '../../../../services/rest/athlete-event.service';
import {HttpResponse} from '@angular/common/http';
import {IAthleteEvent} from '../../../../entities/model/athlete-event.model';

@Component({
    selector: 'app-athlete-events-summary',
    templateUrl: './athlete-events-summary.component.html',
    styleUrls: ['./athlete-events-summary.component.scss']
})
export class AthleteEventsSummaryComponent implements OnInit {

    constructor(private athleteEventService: AthleteEventService) {
    }

    @Input()
    athlete: IAthlete;
    athleteEvents: Array<IAthleteEvent>;

    dropDownEvents: Array<SelectItem> = new Array<SelectItem>();

    ngOnInit() {

        this.athleteEventService.getAthleteEventsByAthleteId(this.athlete.id).subscribe((athleteEventsResponse: HttpResponse<Array<IAthleteEvent>>) => {
           this.athleteEvents = athleteEventsResponse.body;
           for (const athleteEvent of this.athleteEvents) {
               this.dropDownEvents.push({label: athleteEvent.eventName, value: athleteEvent});
           }
        });

        // this.athleteEventService.getAthleteEventSummary(this)

    }

    eventSelected(event: any) {
        this.athleteEventService.getAthleteEventSummary(event.value.eventId, this.athlete.id).subscribe((response: any) => {
            console.log(response);
        });
    }
}
