import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISprawa } from 'app/shared/model/sprawa.model';
import { SprawaService } from './sprawa.service';

@Component({
    selector: 'jhi-sprawa-update',
    templateUrl: './sprawa-update.component.html'
})
export class SprawaUpdateComponent implements OnInit {
    private _sprawa: ISprawa;
    isSaving: boolean;
    utworzonaDp: any;
    dataDp: any;

    constructor(private sprawaService: SprawaService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sprawa }) => {
            this.sprawa = sprawa;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.sprawa.id !== undefined) {
            this.subscribeToSaveResponse(this.sprawaService.update(this.sprawa));
        } else {
            this.subscribeToSaveResponse(this.sprawaService.create(this.sprawa));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISprawa>>) {
        result.subscribe((res: HttpResponse<ISprawa>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get sprawa() {
        return this._sprawa;
    }

    set sprawa(sprawa: ISprawa) {
        this._sprawa = sprawa;
    }
}
