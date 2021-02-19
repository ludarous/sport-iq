import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JHipster6105TestModule } from '../../../test.module';
import { ActivityResultDetailComponent } from 'app/entities/activity-result/activity-result-detail.component';
import { ActivityResult } from 'app/shared/model/activity-result.model';

describe('Component Tests', () => {
  describe('ActivityResult Management Detail Component', () => {
    let comp: ActivityResultDetailComponent;
    let fixture: ComponentFixture<ActivityResultDetailComponent>;
    const route = ({ data: of({ activityResult: new ActivityResult(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JHipster6105TestModule],
        declarations: [ActivityResultDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ActivityResultDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ActivityResultDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load activityResult on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.activityResult).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
