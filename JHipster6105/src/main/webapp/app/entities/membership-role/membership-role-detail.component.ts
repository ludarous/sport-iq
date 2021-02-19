import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMembershipRole } from 'app/shared/model/membership-role.model';

@Component({
  selector: 'jhi-membership-role-detail',
  templateUrl: './membership-role-detail.component.html',
})
export class MembershipRoleDetailComponent implements OnInit {
  membershipRole: IMembershipRole | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ membershipRole }) => (this.membershipRole = membershipRole));
  }

  previousState(): void {
    window.history.back();
  }
}
