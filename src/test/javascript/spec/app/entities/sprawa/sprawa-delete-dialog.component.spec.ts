/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CrmTestModule } from '../../../test.module';
import { SprawaDeleteDialogComponent } from 'app/entities/sprawa/sprawa-delete-dialog.component';
import { SprawaService } from 'app/entities/sprawa/sprawa.service';

describe('Component Tests', () => {
    describe('Sprawa Management Delete Component', () => {
        let comp: SprawaDeleteDialogComponent;
        let fixture: ComponentFixture<SprawaDeleteDialogComponent>;
        let service: SprawaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CrmTestModule],
                declarations: [SprawaDeleteDialogComponent]
            })
                .overrideTemplate(SprawaDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SprawaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SprawaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
