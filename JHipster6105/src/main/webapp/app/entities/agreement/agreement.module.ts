import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipster6105SharedModule } from 'app/shared/shared.module';
import { AgreementComponent } from './agreement.component';
import { AgreementDetailComponent } from './agreement-detail.component';
import { AgreementUpdateComponent } from './agreement-update.component';
import { AgreementDeleteDialogComponent } from './agreement-delete-dialog.component';
import { agreementRoute } from './agreement.route';

@NgModule({
  imports: [JHipster6105SharedModule, RouterModule.forChild(agreementRoute)],
  declarations: [AgreementComponent, AgreementDetailComponent, AgreementUpdateComponent, AgreementDeleteDialogComponent],
  entryComponents: [AgreementDeleteDialogComponent],
})
export class JHipster6105AgreementModule {}
