import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SportIqTestModule } from '../../../test.module';
import { UserActivityDetailComponent } from 'app/entities/user-activity/user-activity-detail.component';
import { UserActivity } from 'app/shared/model/user-activity.model';

describe('Component Tests', () => {
  describe('UserActivity Management Detail Component', () => {
    let comp: UserActivityDetailComponent;
    let fixture: ComponentFixture<UserActivityDetailComponent>;
    const route = ({ data: of({ userActivity: new UserActivity(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportIqTestModule],
        declarations: [UserActivityDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserActivityDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserActivityDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userActivity on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userActivity).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
