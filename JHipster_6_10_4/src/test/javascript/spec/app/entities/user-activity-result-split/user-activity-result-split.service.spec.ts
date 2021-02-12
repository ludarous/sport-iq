import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { UserActivityResultSplitService } from 'app/entities/user-activity-result-split/user-activity-result-split.service';
import { IUserActivityResultSplit, UserActivityResultSplit } from 'app/shared/model/user-activity-result-split.model';

describe('Service Tests', () => {
  describe('UserActivityResultSplit Service', () => {
    let injector: TestBed;
    let service: UserActivityResultSplitService;
    let httpMock: HttpTestingController;
    let elemDefault: IUserActivityResultSplit;
    let expectedResult: IUserActivityResultSplit | IUserActivityResultSplit[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(UserActivityResultSplitService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new UserActivityResultSplit(0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a UserActivityResultSplit', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new UserActivityResultSplit()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a UserActivityResultSplit', () => {
        const returnedFromService = Object.assign(
          {
            value: 1,
            compareValue: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of UserActivityResultSplit', () => {
        const returnedFromService = Object.assign(
          {
            value: 1,
            compareValue: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a UserActivityResultSplit', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
