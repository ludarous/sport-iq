import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SportIqTestModule } from '../../../test.module';
import { UserActivityResultDetailComponent } from 'app/entities/user-activity-result/user-activity-result-detail.component';
import { UserActivityResult } from 'app/shared/model/user-activity-result.model';

describe('Component Tests', () => {
  describe('UserActivityResult Management Detail Component', () => {
    let comp: UserActivityResultDetailComponent;
    let fixture: ComponentFixture<UserActivityResultDetailComponent>;
    const route = ({ data: of({ userActivityResult: new UserActivityResult(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportIqTestModule],
        declarations: [UserActivityResultDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserActivityResultDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserActivityResultDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userActivityResult on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userActivityResult).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
