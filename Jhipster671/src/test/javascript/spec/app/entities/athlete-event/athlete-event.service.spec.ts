import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { AthleteEventService } from 'app/entities/athlete-event/athlete-event.service';
import { IAthleteEvent, AthleteEvent } from 'app/shared/model/athlete-event.model';

describe('Service Tests', () => {
  describe('AthleteEvent Service', () => {
    let injector: TestBed;
    let service: AthleteEventService;
    let httpMock: HttpTestingController;
    let elemDefault: IAthleteEvent;
    let expectedResult: IAthleteEvent | IAthleteEvent[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AthleteEventService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new AthleteEvent(0, 'AAAAAAA', 0, 0, false, currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            registrationDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a AthleteEvent', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            registrationDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            registrationDate: currentDate
          },
          returnedFromService
        );

        service.create(new AthleteEvent()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AthleteEvent', () => {
        const returnedFromService = Object.assign(
          {
            note: 'BBBBBB',
            actualHeightInCm: 1,
            actualWeightInKg: 1,
            medicalFitnessAgreement: true,
            registrationDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            registrationDate: currentDate
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AthleteEvent', () => {
        const returnedFromService = Object.assign(
          {
            note: 'BBBBBB',
            actualHeightInCm: 1,
            actualWeightInKg: 1,
            medicalFitnessAgreement: true,
            registrationDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            registrationDate: currentDate
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a AthleteEvent', () => {
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
