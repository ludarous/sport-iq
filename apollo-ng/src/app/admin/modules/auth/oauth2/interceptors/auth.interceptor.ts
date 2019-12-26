import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {CsrfService} from '../../services/csrf.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(private csrfService: CsrfService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const csrfToken = this.csrfService.getCsrfToken();
    const setHeaders = !!csrfToken ? {'X-XSRF-TOKEN': csrfToken} : {}
    request = request.clone({
      withCredentials: true,
      setHeaders: setHeaders
    });
    return next.handle(request);
  }
}
