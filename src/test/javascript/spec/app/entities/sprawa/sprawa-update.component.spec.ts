/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CrmTestModule } from '../../../test.module';
import { SprawaUpdateComponent } from 'app/entities/sprawa/sprawa-update.component';
import { SprawaService } from 'app/entities/sprawa/sprawa.service';
import { Sprawa } from 'app/shared/model/sprawa.model';

describe('Component Tests', () => {
    describe('Sprawa Management Update Component', () => {
        let comp: SprawaUpdateComponent;
        let fixture: ComponentFixture<SprawaUpdateComponent>;
        let service: SprawaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CrmTestModule],
                declarations: [SprawaUpdateComponent]
            })
                .overrideTemplate(SprawaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SprawaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SprawaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Sprawa(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sprawa = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Sprawa();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sprawa = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
