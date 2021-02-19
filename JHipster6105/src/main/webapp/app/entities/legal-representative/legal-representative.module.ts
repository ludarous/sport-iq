import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JHipster6105SharedModule } from 'app/shared/shared.module';
import { LegalRepresentativeComponent } from './legal-representative.component';
import { LegalRepresentativeDetailComponent } from './legal-representative-detail.component';
import { LegalRepresentativeUpdateComponent } from './legal-representative-update.component';
import { LegalRepresentativeDeleteDialogComponent } from './legal-representative-delete-dialog.component';
import { legalRepresentativeRoute } from './legal-representative.route';

@NgModule({
  imports: [JHipster6105SharedModule, RouterModule.forChild(legalRepresentativeRoute)],
  declarations: [
    LegalRepresentativeComponent,
    LegalRepresentativeDetailComponent,
    LegalRepresentativeUpdateComponent,
    LegalRepresentativeDeleteDialogComponent,
  ],
  entryComponents: [LegalRepresentativeDeleteDialogComponent],
})
export class JHipster6105LegalRepresentativeModule {}
