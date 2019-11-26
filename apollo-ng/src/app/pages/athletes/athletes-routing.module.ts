import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {AthletesListComponent} from './list/athletes-list.component';
import {AthletesEditComponent} from './edit/athletes-edit.component';
import {AthletesComponent} from './athletes.component';

const routes: Routes = [
  {
    path: '',
    component: AthletesComponent,
    children: [
      {
        path: '',
        redirectTo: 'list'
      },
      {
        path: 'list',
        component: AthletesListComponent
      },
      {
        path: 'create',
        component: AthletesEditComponent
      },
      {
        path: 'edit/:id',
        component: AthletesEditComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AthletesRoutingModule {
}
