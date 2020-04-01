import {Component, Input, OnInit} from '@angular/core';
import {IAthlete} from '../../../../../entities/model/athlete.model';
import {IEvent, Event} from '../../../../../entities/model/event.model';
import {DropdownItem, SelectItem} from 'primeng/primeng';
import {AthleteEventService} from '../../../../../services/rest/athlete-event.service';
import {HttpResponse} from '@angular/common/http';
import {IAthleteEvent} from '../../../../../entities/model/athlete-event.model';
import {AthleteEventSummary} from '../../../../../entities/summaries/athlete-event-summary';

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

    selectedAthleteEvent: IAthleteEvent;

    dropDownEvents: Array<SelectItem> = new Array<SelectItem>();

    athleteEventSummary: AthleteEventSummary;

    ngOnInit() {

        this.athleteEventService.getAthleteEventsByAthleteId(this.athlete.id).subscribe((athleteEventsResponse: HttpResponse<Array<IAthleteEvent>>) => {
           this.athleteEvents = athleteEventsResponse.body;
           for (const athleteEvent of this.athleteEvents) {
               this.dropDownEvents.push({label: athleteEvent.eventName, value: athleteEvent});
           }

           if (this.athleteEvents.length === 1) {
               this.selectAthleteEvent(this.athleteEvents[0]);
           }
        });

        // this.athleteEventService.getAthleteEventSummary(this)

    }

    eventSelected(event: any) {
        this.athleteEventService.getAthleteEventSummary(event.value.eventId, this.athlete.id).subscribe((response: HttpResponse<AthleteEventSummary>) => {
            this.athleteEventSummary = response.body;
        });
    }

    selectAthleteEvent(athleteEvent: IAthleteEvent) {
        this.selectedAthleteEvent = athleteEvent;
        this.athleteEventService.getAthleteEventSummary(athleteEvent.eventId, this.athlete.id).subscribe((response: HttpResponse<AthleteEventSummary>) => {
            this.athleteEventSummary = response.body;
        });
    }
}
