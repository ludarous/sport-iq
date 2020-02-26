import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AthleteActivityResultSplitService } from 'app/entities/athlete-activity-result-split/athlete-activity-result-split.service';
import { IAthleteActivityResultSplit, AthleteActivityResultSplit } from 'app/shared/model/athlete-activity-result-split.model';

describe('Service Tests', () => {
  describe('AthleteActivityResultSplit Service', () => {
    let injector: TestBed;
    let service: AthleteActivityResultSplitService;
    let httpMock: HttpTestingController;
    let elemDefault: IAthleteActivityResultSplit;
    let expectedResult: IAthleteActivityResultSplit | IAthleteActivityResultSplit[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AthleteActivityResultSplitService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new AthleteActivityResultSplit(0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AthleteActivityResultSplit', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AthleteActivityResultSplit()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AthleteActivityResultSplit', () => {
        const returnedFromService = Object.assign(
          {
            value: 1,
            compareValue: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AthleteActivityResultSplit', () => {
        const returnedFromService = Object.assign(
          {
            value: 1,
            compareValue: 1
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

      it('should delete a AthleteActivityResultSplit', () => {
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
