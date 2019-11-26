import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { EventsListComponent } from './list/events-list.component';
import { EventsEditComponent } from './edit/events-edit.component';
import { EventsComponent } from './events.component';
import { EventsResultsComponent } from './results/events-results.component';

const routes: Routes = [
    {
        path: '',
        component: EventsComponent,
        children: [
            {
                path: '',
                redirectTo: 'list'
            },
            {
                path: 'list',
                component: EventsListComponent
            },
            {
                path: 'create',
                component: EventsEditComponent
            },
            {
                path: 'edit/:id',
                component: EventsEditComponent
            },
            {
                path: 'results/:id',
                component: EventsResultsComponent
            }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class EventsRoutingModule {
}
