import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOrganisationMembership } from 'app/shared/model/organisation-membership.model';

@Component({
  selector: 'jhi-organisation-membership-detail',
  templateUrl: './organisation-membership-detail.component.html',
})
export class OrganisationMembershipDetailComponent implements OnInit {
  organisationMembership: IOrganisationMembership | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ organisationMembership }) => (this.organisationMembership = organisationMembership));
  }

  previousState(): void {
    window.history.back();
  }
}
