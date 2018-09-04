/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CrmTestModule } from '../../../test.module';
import { SprawaDetailComponent } from 'app/entities/sprawa/sprawa-detail.component';
import { Sprawa } from 'app/shared/model/sprawa.model';

describe('Component Tests', () => {
    describe('Sprawa Management Detail Component', () => {
        let comp: SprawaDetailComponent;
        let fixture: ComponentFixture<SprawaDetailComponent>;
        const route = ({ data: of({ sprawa: new Sprawa(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CrmTestModule],
                declarations: [SprawaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SprawaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SprawaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sprawa).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
