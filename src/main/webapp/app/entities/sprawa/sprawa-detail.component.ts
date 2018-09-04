import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISprawa } from 'app/shared/model/sprawa.model';

@Component({
    selector: 'jhi-sprawa-detail',
    templateUrl: './sprawa-detail.component.html'
})
export class SprawaDetailComponent implements OnInit {
    sprawa: ISprawa;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sprawa }) => {
            this.sprawa = sprawa;
        });
    }

    previousState() {
        window.history.back();
    }
}
