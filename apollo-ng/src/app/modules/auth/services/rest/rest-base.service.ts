import { HttpClient, HttpErrorResponse, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IUser } from '../../../entities/user';
import { environment } from '../../../../../environments/environment';
import { Injectable, Injector } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { ToastService } from '../../../core/services/message.service';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { catchError, map } from 'rxjs/operators';

@Injectable()
export class CrudBaseService<T> {


    constructor(apiUrl: string, resourceUrl: string, injector: Injector, mappers?: ((item: T, isRequest: boolean) => T)[]) {
        this.resourceUrl = resourceUrl;
        this.apiUrl = apiUrl;
        this.mappers = mappers;
        this.http = injector.get(HttpClient);
        this.translateService = injector.get(TranslateService);
        this.toastService = injector.get(ToastService);
        this.authService = injector.get(AuthService);
        this.router = injector.get(Router);
    }

    readonly resourceUrl;
    private apiUrl;

    protected http: HttpClient;
    protected translateService: TranslateService;
    protected toastService: ToastService;
    protected authService: AuthService;
    protected router: Router;

    private mappers: ((item: T, isRequest: boolean) => T)[];

    errorMap = (error: any, caught): Observable<any> => {
        throw this.handleErrorResponse(error);
    }
    responseMap = (response: HttpResponse<any>) => this.mapResponse(response);

    create(entity: T): Observable<HttpResponse<T>> {
        // entity = this.mapRequestBody(entity);
        return this.http.post<T>(this.apiUrl + this.resourceUrl, entity, {observe: 'response'})
            .pipe(map(this.responseMap), catchError(this.errorMap));
    }

    update(entity: T): Observable<HttpResponse<T>> {
        // entity = this.mapRequestBody(entity);
        return this.http.put<T>(this.apiUrl + this.resourceUrl, entity, {observe: 'response'})
            .pipe(map(this.responseMap), catchError(this.errorMap));
    }

    find(id: string | number): Observable<HttpResponse<T>> {
        return this.http.get<T>(`${this.apiUrl + this.resourceUrl}/${id}`, {observe: 'response'})
            .pipe(map(this.responseMap), catchError(this.errorMap));
    }

    query(params?: any): Observable<HttpResponse<T[]>> {
        const options = this.createRequestOption(params);
        return this.http.get<T[]>(this.apiUrl + this.resourceUrl, {params: options, observe: 'response'})
            .pipe(map(this.responseMap), catchError(this.errorMap));
    }

    remove(id: string | number): Observable<HttpResponse<any>> {
        return this.http.delete(`${this.apiUrl + this.resourceUrl}/${id}`, {observe: 'response'});
    }

    createRequestOption(params?: any): HttpParams {
        if (!params) {
            params = {
                page: 0,
                size: 1000,
            };
        }
        let options: HttpParams = new HttpParams();
        if (params) {
            Object.keys(params).forEach(key => {
                if (key !== 'sort') {
                    options = options.set(key, params[key]);
                }
            });
            if (params.sort) {
                params.sort.forEach(val => {
                    options = options.append('sort', val);
                });
            }
        }
        return options;
    }

    public post(endpointUrl: string, body: any, params?: any): Observable<HttpResponse<any>> {
        // body = this.mapRequestBody(body);
        return this.http.post(this.apiUrl + endpointUrl, body, {params: params, observe: 'response'})
            .pipe(map(this.responseMap), catchError(this.errorMap));
    }

    public put(endpointUrl: string, body?: any, params?: any): Observable<HttpResponse<any>> {
        // body = this.mapRequestBody(body);
        return this.http.put(this.apiUrl + endpointUrl, body, {params: params, observe: 'response'})
            .pipe(map(this.responseMap), catchError(this.errorMap));
    }

    public get(endpointUrl: string, params?: any): Observable<HttpResponse<any>> {
        return this.http.get(this.apiUrl + endpointUrl, {params: params, observe: 'response'})
            .pipe(map(this.responseMap), catchError(this.errorMap));
    }

    public delete(endpointUrl: string): Observable<HttpResponse<any>> {
        return this.http.delete(this.apiUrl + endpointUrl, {observe: 'response'});
    }

    public getFile(endpointUrl: string, params?: any, responseType?: any): Observable<HttpResponse<Blob>> {
        return this.http.get(this.apiUrl + endpointUrl, {params: params, observe: 'response', responseType: 'blob'});
    }


    mapRequestBody(entity: any): any {
        if (this.mappers && this.mappers.length > 0) {
            for (const mapper of this.mappers) {
                mapper(entity, true);
            }
        }
        return entity;
    }

    mapResponse(response: HttpResponse<any>): HttpResponse<any> {
        if (this.mappers && this.mappers.length > 0) {
            for (const mapper of this.mappers) {
                if (response.body instanceof Array) {
                    for (const item of response.body) {
                        mapper(item, false);
                    }
                } else {
                    mapper(response.body, false);
                }
            }
        }
        return response;
    }

    private handleErrorResponse(error: HttpErrorResponse): HttpErrorResponse {
        if (error.status === 401) {
            this.authService.updateUser().subscribe(
                (user: IUser) => {
                    this.toastService.showError(
                        this.translateService.instant('Přístup zamítnut.'),
                        this.translateService.instant('Je nám líto, ale na tuto stránku nemáte oprávněný přístup.')
                    );
                },
                (accountError: HttpErrorResponse) => {
                    if (accountError.status === 401) {
                        this.toastService.showWarn(
                            this.translateService.instant('Přihlášení vypršelo'),
                            this.translateService.instant('Je nám líto, ale přihlášení k aplikaci vypršelo. Prosím, přihlaste se znovu.')
                        );
                        this.router.navigate(['/']);
                    }
                }
            );
        } else if (error.status === 500) {
            this.toastService.showError(
                this.translateService.instant('Chyba'),
                this.translateService.instant('Nastala chyba na serveru')
            );
        }

        return error;
    }
}
