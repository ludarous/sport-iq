import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ActivityCategoriesListComponent} from './list/activity-categories-list.component';
import {ActivityCategoriesEditComponent} from './edit/activity-categories-edit.component';
import {ActivityCategoriesComponent} from './activity-categories.component';

const routes: Routes = [
  {
    path: '',
    component: ActivityCategoriesComponent,
    children: [
      {
        path: '',
        redirectTo: 'list'
      },
      {
        path: 'list',
        component: ActivityCategoriesListComponent
      },
      {
        path: 'create',
        component: ActivityCategoriesEditComponent
      },
      {
        path: 'edit/:id',
        component: ActivityCategoriesEditComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ActivityCategoriesRoutingModule {
}
