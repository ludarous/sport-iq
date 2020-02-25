import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {EventLocationsListComponent} from './list/event-locations-list.component';
import {EventLocationsEditComponent} from './edit/event-locations-edit.component';
import {EventLocationsComponent} from './event-locations.component';

const routes: Routes = [
  {
    path: '',
    component: EventLocationsComponent,
    children: [
      {
        path: '',
        redirectTo: 'list'
      },
      {
        path: 'list',
        component: EventLocationsListComponent
      },
      {
        path: 'create',
        component: EventLocationsEditComponent
      },
      {
        path: 'edit/:id',
        component: EventLocationsEditComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class EventLocationsRoutingModule {
}
