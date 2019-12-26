import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'event',
        loadChildren: () => import('./event/event.module').then(m => m.SportiqEventModule)
      },
      {
        path: 'workout',
        loadChildren: () => import('./workout/workout.module').then(m => m.SportiqWorkoutModule)
      },
      {
        path: 'activity',
        loadChildren: () => import('./activity/activity.module').then(m => m.SportiqActivityModule)
      },
      {
        path: 'activity-result',
        loadChildren: () => import('./activity-result/activity-result.module').then(m => m.SportiqActivityResultModule)
      },
      {
        path: 'activity-result-split',
        loadChildren: () => import('./activity-result-split/activity-result-split.module').then(m => m.SportiqActivityResultSplitModule)
      },
      {
        path: 'workout-category',
        loadChildren: () => import('./workout-category/workout-category.module').then(m => m.SportiqWorkoutCategoryModule)
      },
      {
        path: 'activity-category',
        loadChildren: () => import('./activity-category/activity-category.module').then(m => m.SportiqActivityCategoryModule)
      },
      {
        path: 'athlete-event',
        loadChildren: () => import('./athlete-event/athlete-event.module').then(m => m.SportiqAthleteEventModule)
      },
      {
        path: 'athlete-workout',
        loadChildren: () => import('./athlete-workout/athlete-workout.module').then(m => m.SportiqAthleteWorkoutModule)
      },
      {
        path: 'athlete-activity',
        loadChildren: () => import('./athlete-activity/athlete-activity.module').then(m => m.SportiqAthleteActivityModule)
      },
      {
        path: 'athlete-activity-result',
        loadChildren: () =>
          import('./athlete-activity-result/athlete-activity-result.module').then(m => m.SportiqAthleteActivityResultModule)
      },
      {
        path: 'athlete-activity-result-split',
        loadChildren: () =>
          import('./athlete-activity-result-split/athlete-activity-result-split.module').then(
            m => m.SportiqAthleteActivityResultSplitModule
          )
      },
      {
        path: 'athlete',
        loadChildren: () => import('./athlete/athlete.module').then(m => m.SportiqAthleteModule)
      },
      {
        path: 'address',
        loadChildren: () => import('./address/address.module').then(m => m.SportiqAddressModule)
      },
      {
        path: 'sport',
        loadChildren: () => import('./sport/sport.module').then(m => m.SportiqSportModule)
      },
      {
        path: 'unit',
        loadChildren: () => import('./unit/unit.module').then(m => m.SportiqUnitModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class SportiqEntityModule {}
