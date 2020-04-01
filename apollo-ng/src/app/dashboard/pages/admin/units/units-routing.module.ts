import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {UnitsListComponent} from './list/units-list.component';
import {UnitsEditComponent} from './edit/units-edit.component';
import {UnitsComponent} from './units.component';

const routes: Routes = [
  {
    path: '',
    component: UnitsComponent,
    children: [
      {
        path: '',
        redirectTo: 'list'
      },
      {
        path: 'list',
        component: UnitsListComponent
      },
      {
        path: 'create',
        component: UnitsEditComponent
      },
      {
        path: 'edit/:id',
        component: UnitsEditComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UnitsRoutingModule {
}
