import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {WorkoutsListComponent} from './list/workouts-list.component';
import {WorkoutsEditComponent} from './edit/workouts-edit.component';
import {WorkoutsComponent} from './workouts.component';

const routes: Routes = [
  {
    path: '',
    component: WorkoutsComponent,
    children: [
      {
        path: '',
        redirectTo: 'list'
      },
      {
        path: 'list',
        component: WorkoutsListComponent
      },
      {
        path: 'create',
        component: WorkoutsEditComponent
      },
      {
        path: 'edit/:id',
        component: WorkoutsEditComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class WorkoutsRoutingModule {
}
