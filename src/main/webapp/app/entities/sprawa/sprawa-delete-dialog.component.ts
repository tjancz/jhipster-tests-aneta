import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISprawa } from 'app/shared/model/sprawa.model';
import { SprawaService } from './sprawa.service';

@Component({
    selector: 'jhi-sprawa-delete-dialog',
    templateUrl: './sprawa-delete-dialog.component.html'
})
export class SprawaDeleteDialogComponent {
    sprawa: ISprawa;

    constructor(private sprawaService: SprawaService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sprawaService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'sprawaListModification',
                content: 'Deleted an sprawa'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sprawa-delete-popup',
    template: ''
})
export class SprawaDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sprawa }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SprawaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.sprawa = sprawa;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
