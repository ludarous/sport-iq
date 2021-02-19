import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JHipster6105TestModule } from '../../../test.module';
import { ActivityResultSplitDetailComponent } from 'app/entities/activity-result-split/activity-result-split-detail.component';
import { ActivityResultSplit } from 'app/shared/model/activity-result-split.model';

describe('Component Tests', () => {
  describe('ActivityResultSplit Management Detail Component', () => {
    let comp: ActivityResultSplitDetailComponent;
    let fixture: ComponentFixture<ActivityResultSplitDetailComponent>;
    const route = ({ data: of({ activityResultSplit: new ActivityResultSplit(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [ActivityResultSplitDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ActivityResultSplitDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ActivityResultSplitDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load activityResultSplit on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.activityResultSplit).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
