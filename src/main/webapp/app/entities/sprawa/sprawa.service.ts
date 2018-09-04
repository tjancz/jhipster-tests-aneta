import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISprawa } from 'app/shared/model/sprawa.model';

type EntityResponseType = HttpResponse<ISprawa>;
type EntityArrayResponseType = HttpResponse<ISprawa[]>;

@Injectable({ providedIn: 'root' })
export class SprawaService {
    private resourceUrl = SERVER_API_URL + 'api/sprawas';

    constructor(private http: HttpClient) {}

    create(sprawa: ISprawa): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(sprawa);
        return this.http
            .post<ISprawa>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(sprawa: ISprawa): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(sprawa);
        return this.http
            .put<ISprawa>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISprawa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISprawa[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(sprawa: ISprawa): ISprawa {
        const copy: ISprawa = Object.assign({}, sprawa, {
            utworzona: sprawa.utworzona != null && sprawa.utworzona.isValid() ? sprawa.utworzona.format(DATE_FORMAT) : null,
            data: sprawa.data != null && sprawa.data.isValid() ? sprawa.data.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.utworzona = res.body.utworzona != null ? moment(res.body.utworzona) : null;
        res.body.data = res.body.data != null ? moment(res.body.data) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((sprawa: ISprawa) => {
            sprawa.utworzona = sprawa.utworzona != null ? moment(sprawa.utworzona) : null;
            sprawa.data = sprawa.data != null ? moment(sprawa.data) : null;
        });
        return res;
    }
}
