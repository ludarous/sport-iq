import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SportiqEventModule } from './event/event.module';
import { SportiqWorkoutModule } from './workout/workout.module';
import { SportiqActivityModule } from './activity/activity.module';
import { SportiqActivityResultModule } from './activity-result/activity-result.module';
import { SportiqActivityResultSplitModule } from './activity-result-split/activity-result-split.module';
import { SportiqWorkoutCategoryModule } from './workout-category/workout-category.module';
import { SportiqActivityCategoryModule } from './activity-category/activity-category.module';
import { SportiqAthleteEventModule } from './athlete-event/athlete-event.module';
import { SportiqAthleteWorkoutModule } from './athlete-workout/athlete-workout.module';
import { SportiqAthleteActivityModule } from './athlete-activity/athlete-activity.module';
import { SportiqAthleteActivityResultModule } from './athlete-activity-result/athlete-activity-result.module';
import { SportiqAthleteActivityResultSplitModule } from './athlete-activity-result-split/athlete-activity-result-split.module';
import { SportiqAthleteModule } from './athlete/athlete.module';
import { SportiqAddressModule } from './address/address.module';
import { SportiqSportModule } from './sport/sport.module';
import { SportiqUnitModule } from './unit/unit.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SportiqEventModule,
        SportiqWorkoutModule,
        SportiqActivityModule,
        SportiqActivityResultModule,
        SportiqActivityResultSplitModule,
        SportiqWorkoutCategoryModule,
        SportiqActivityCategoryModule,
        SportiqAthleteEventModule,
        SportiqAthleteWorkoutModule,
        SportiqAthleteActivityModule,
        SportiqAthleteActivityResultModule,
        SportiqAthleteActivityResultSplitModule,
        SportiqAthleteModule,
        SportiqAddressModule,
        SportiqSportModule,
        SportiqUnitModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SportiqEntityModule {}
