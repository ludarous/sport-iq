import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';


const routes: Routes = [
    {
        path: 'event-locations',
        loadChildren: () => import('./event-locations/event-locations.module').then(m => m.EventLocationsModule)
    },
    {
        path: 'activity-categories',
        loadChildren: () => import('./activity-categories/activity-categories.module').then(m => m.ActivityCategoriesModule)
    },
    {
        path: 'units',
        loadChildren: () => import('./units/units.module').then(m => m.UnitsModule)},
    {
        path: 'activities',
        loadChildren: () => import('./activities/activities.module').then(m => m.ActivitiesModule)
    },
    {
        path: 'workouts',
        loadChildren: () => import('./workouts/workouts.module').then(m => m.WorkoutsModule)
    },
    {
        path: 'athletes',
        loadChildren: () => import('./athletes/athletes.module').then(m => m.AthletesModule)
    },
    {
        path: 'events',
        loadChildren: () => import('./events/events.module').then(m => m.EventsModule)
    }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
