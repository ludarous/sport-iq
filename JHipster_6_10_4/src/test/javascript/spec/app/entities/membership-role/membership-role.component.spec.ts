import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, convertToParamMap } from '@angular/router';

import { SportIqTestModule } from '../../../test.module';
import { MembershipRoleComponent } from 'app/entities/membership-role/membership-role.component';
import { MembershipRoleService } from 'app/entities/membership-role/membership-role.service';
import { MembershipRole } from 'app/shared/model/membership-role.model';

describe('Component Tests', () => {
  describe('MembershipRole Management Component', () => {
    let comp: MembershipRoleComponent;
    let fixture: ComponentFixture<MembershipRoleComponent>;
    let service: MembershipRoleService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SportIqTestModule],
        declarations: [MembershipRoleComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: {
              data: of({
                defaultSort: 'id,asc',
              }),
              queryParamMap: of(
                convertToParamMap({
                  page: '1',
                  size: '1',
                  sort: 'id,desc',
                })
              ),
            },
          },
        ],
      })
        .overrideTemplate(MembershipRoleComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MembershipRoleComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MembershipRoleService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MembershipRole(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.membershipRoles && comp.membershipRoles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MembershipRole(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.membershipRoles && comp.membershipRoles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      comp.ngOnInit();
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,desc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // INIT
      comp.ngOnInit();

      // GIVEN
      comp.predicate = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,desc', 'id']);
    });
  });
});
