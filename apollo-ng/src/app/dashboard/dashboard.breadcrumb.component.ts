import { Component, OnDestroy } from '@angular/core';
import { DashboardComponent } from './dashboard.component';
import { BreadcrumbService } from './breadcrumb.service';
import { Subscription } from 'rxjs';
import { MenuItem } from 'primeng/primeng';

@Component({
    selector: 'app-breadcrumb',
    templateUrl: './dashboard.breadcrumb.component.html'
})
export class DashboardBreadcrumbComponent implements OnDestroy {

    subscription: Subscription;

    items: MenuItem[];

    constructor(public breadcrumbService: BreadcrumbService) {
        this.subscription = breadcrumbService.itemsHandler.subscribe(response => {
            this.items = response;
        });
    }

    ngOnDestroy() {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }
}
