/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CrmTestModule } from '../../../test.module';
import { SprawaComponent } from 'app/entities/sprawa/sprawa.component';
import { SprawaService } from 'app/entities/sprawa/sprawa.service';
import { Sprawa } from 'app/shared/model/sprawa.model';

describe('Component Tests', () => {
    describe('Sprawa Management Component', () => {
        let comp: SprawaComponent;
        let fixture: ComponentFixture<SprawaComponent>;
        let service: SprawaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CrmTestModule],
                declarations: [SprawaComponent],
                providers: []
            })
                .overrideTemplate(SprawaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SprawaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SprawaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Sprawa(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.sprawas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
