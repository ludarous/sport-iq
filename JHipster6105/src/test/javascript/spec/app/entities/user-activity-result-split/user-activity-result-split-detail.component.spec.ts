import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JHipster6105TestModule } from '../../../test.module';
import { UserActivityResultSplitDetailComponent } from 'app/entities/user-activity-result-split/user-activity-result-split-detail.component';
import { UserActivityResultSplit } from 'app/shared/model/user-activity-result-split.model';

describe('Component Tests', () => {
  describe('UserActivityResultSplit Management Detail Component', () => {
    let comp: UserActivityResultSplitDetailComponent;
    let fixture: ComponentFixture<UserActivityResultSplitDetailComponent>;
    const route = ({ data: of({ userActivityResultSplit: new UserActivityResultSplit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [UserActivityResultSplitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(UserActivityResultSplitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(UserActivityResultSplitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load userActivityResultSplit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.userActivityResultSplit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
