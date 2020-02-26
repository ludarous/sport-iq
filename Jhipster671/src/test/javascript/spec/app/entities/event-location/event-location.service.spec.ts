import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { EventLocationService } from 'app/entities/event-location/event-location.service';
import { IEventLocation, EventLocation } from 'app/shared/model/event-location.model';

describe('Service Tests', () => {
  describe('EventLocation Service', () => {
    let injector: TestBed;
    let service: EventLocationService;
    let httpMock: HttpTestingController;
    let elemDefault: IEventLocation;
    let expectedResult: IEventLocation | IEventLocation[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EventLocationService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new EventLocation(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0, 0, 'AAAAAAA', 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a EventLocation', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new EventLocation()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EventLocation', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            state: 'BBBBBB',
            city: 'BBBBBB',
            street: 'BBBBBB',
            streetNumber: 'BBBBBB',
            latitude: 1,
            longitude: 1,
            capacity: 1,
            mapLink: 'BBBBBB',
            maxAge: 1,
            minAge: 1
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of EventLocation', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            state: 'BBBBBB',
            city: 'BBBBBB',
            street: 'BBBBBB',
            streetNumber: 'BBBBBB',
            latitude: 1,
            longitude: 1,
            capacity: 1,
            mapLink: 'BBBBBB',
            maxAge: 1,
            minAge: 1
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

      it('should delete a EventLocation', () => {
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
