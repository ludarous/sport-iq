import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SportIqSharedModule } from 'app/shared/shared.module';
import { LegalRepresentativeComponent } from './legal-representative.component';
import { LegalRepresentativeDetailComponent } from './legal-representative-detail.component';
import { LegalRepresentativeUpdateComponent } from './legal-representative-update.component';
import { LegalRepresentativeDeleteDialogComponent } from './legal-representative-delete-dialog.component';
import { legalRepresentativeRoute } from './legal-representative.route';

@NgModule({
  imports: [SportIqSharedModule, RouterModule.forChild(legalRepresentativeRoute)],
  declarations: [
    LegalRepresentativeComponent,
    LegalRepresentativeDetailComponent,
    LegalRepresentativeUpdateComponent,
    LegalRepresentativeDeleteDialogComponent,
  ],
  entryComponents: [LegalRepresentativeDeleteDialogComponent],
})
export class SportIqLegalRepresentativeModule {}
