import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipster6105SharedModule } from 'app/shared/shared.module';
import { AddressComponent } from './address.component';
import { AddressDetailComponent } from './address-detail.component';
import { AddressUpdateComponent } from './address-update.component';
import { AddressDeleteDialogComponent } from './address-delete-dialog.component';
import { addressRoute } from './address.route';

@NgModule({
  imports: [JHipster6105SharedModule, RouterModule.forChild(addressRoute)],
  declarations: [AddressComponent, AddressDetailComponent, AddressUpdateComponent, AddressDeleteDialogComponent],
  entryComponents: [AddressDeleteDialogComponent],
})
export class JHipster6105AddressModule {}
