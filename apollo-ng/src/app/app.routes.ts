import { Routes, RouterModule } from '@angular/router';
import { ModuleWithProviders } from '@angular/core';
import { DashboardDemoComponent } from './demo/view/dashboarddemo.component';
import { SampleDemoComponent } from './demo/view/sampledemo.component';
import { FormsDemoComponent } from './demo/view/formsdemo.component';
import { DataDemoComponent } from './demo/view/datademo.component';
import { PanelsDemoComponent } from './demo/view/panelsdemo.component';
import { OverlaysDemoComponent } from './demo/view/overlaysdemo.component';
import { MenusDemoComponent } from './demo/view/menusdemo.component';
import { MessagesDemoComponent } from './demo/view/messagesdemo.component';
import { ChartsDemoComponent } from './demo/view/chartsdemo.component';
import { FileDemoComponent } from './demo/view/filedemo.component';
import { MiscDemoComponent } from './demo/view/miscdemo.component';
import { EmptyDemoComponent } from './demo/view/emptydemo.component';
import { DocumentationComponent } from './demo/view/documentation.component';
import {ActivityCategoriesModule} from './pages/activity-categories/activity-categories.module';
import {UnitsModule} from './pages/units/units.module';
import {ActivitiesModule} from './pages/activities/activities.module';
import {WorkoutsModule} from './pages/workouts/workouts.module';
import {AthletesModule} from './pages/athletes/athletes.module';
import {EventsModule} from './pages/events/events.module';

export const routes: Routes = [
    { path: '', component: DashboardDemoComponent },
    { path: 'sample', component: SampleDemoComponent },
    { path: 'forms', component: FormsDemoComponent },
    { path: 'data', component: DataDemoComponent },
    { path: 'panels', component: PanelsDemoComponent },
    { path: 'overlays', component: OverlaysDemoComponent },
    { path: 'menus', component: MenusDemoComponent },
    { path: 'messages', component: MessagesDemoComponent },
    { path: 'charts', component: ChartsDemoComponent },
    { path: 'file', component: FileDemoComponent },
    { path: 'misc', component: MiscDemoComponent },
    { path: 'empty', component: EmptyDemoComponent },
    { path: 'documentation', component: DocumentationComponent },
    { path: 'activity-categories', loadChildren: () => ActivityCategoriesModule},
    { path: 'units', loadChildren: () => UnitsModule},
    { path: 'activities', loadChildren: () => ActivitiesModule},
    { path: 'workouts', loadChildren: () => WorkoutsModule},
    { path: 'athletes', loadChildren: () => AthletesModule},
    { path: 'events', loadChildren: () => EventsModule}
];

export const AppRoutes: ModuleWithProviders = RouterModule.forRoot(routes, {scrollPositionRestoration: 'enabled'});
