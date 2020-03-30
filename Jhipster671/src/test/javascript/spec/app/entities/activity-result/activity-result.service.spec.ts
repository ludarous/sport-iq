import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ActivityResultService } from 'app/entities/activity-result/activity-result.service';
import { IActivityResult, ActivityResult } from 'app/shared/model/activity-result.model';
import { ResultType } from 'app/shared/model/enumerations/result-type.model';

describe('Service Tests', () => {
  describe('ActivityResult Service', () => {
    let injector: TestBed;
    let service: ActivityResultService;
    let httpMock: HttpTestingController;
    let elemDefault: IActivityResult;
    let expectedResult: IActivityResult | IActivityResult[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ActivityResultService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ActivityResult(0, 'AAAAAAA', ResultType.LESS_IS_BETTER, 0, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ActivityResult', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new ActivityResult()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ActivityResult', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            resultType: 'BBBBBB',
            ratingWeight: 1,
            mainResult: true
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ActivityResult', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            resultType: 'BBBBBB',
            ratingWeight: 1,
            mainResult: true
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

      it('should delete a ActivityResult', () => {
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
