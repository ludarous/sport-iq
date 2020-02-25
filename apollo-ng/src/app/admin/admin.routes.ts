import { RouterModule, Routes } from '@angular/router';
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
import { AdminComponent } from './admin.component';

export const routes: Routes = [
    {
        path: '', component: AdminComponent, children: [
            {path: '', component: DashboardDemoComponent},
            {path: 'sample', component: SampleDemoComponent},
            {path: 'forms', component: FormsDemoComponent},
            {path: 'data', component: DataDemoComponent},
            {path: 'panels', component: PanelsDemoComponent},
            {path: 'overlays', component: OverlaysDemoComponent},
            {path: 'menus', component: MenusDemoComponent},
            {path: 'messages', component: MessagesDemoComponent},
            {path: 'charts', component: ChartsDemoComponent},
            {path: 'file', component: FileDemoComponent},
            {path: 'misc', component: MiscDemoComponent},
            {path: 'empty', component: EmptyDemoComponent},
            {path: 'documentation', component: DocumentationComponent},
            {
                path: 'event-locations',
                loadChildren: () => import('./pages/event-locations/event-locations.module').then(m => m.EventLocationsModule)
            },
            {
                path: 'activity-categories',
                loadChildren: () => import('./pages/activity-categories/activity-categories.module').then(m => m.ActivityCategoriesModule)
            },
            {path: 'units', loadChildren: () => import('./pages/units/units.module').then(m => m.UnitsModule)},
            {
                path: 'activities',
                loadChildren: () => import('./pages/activities/activities.module').then(m => m.ActivitiesModule)
            },
            {
                path: 'workouts',
                loadChildren: () => import('./pages/workouts/workouts.module').then(m => m.WorkoutsModule)
            },
            {
                path: 'athletes',
                loadChildren: () => import('./pages/athletes/athletes.module').then(m => m.AthletesModule)
            },
            {path: 'events', loadChildren: () => import('./pages/events/events.module').then(m => m.EventsModule)}
        ]
    },

];

export const AdminRoutes: ModuleWithProviders = RouterModule.forChild(routes);
