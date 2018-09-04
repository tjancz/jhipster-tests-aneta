import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { CrmSharedModule } from 'app/shared';
import {
    SprawaComponent,
    SprawaDetailComponent,
    SprawaUpdateComponent,
    SprawaDeletePopupComponent,
    SprawaDeleteDialogComponent,
    sprawaRoute,
    sprawaPopupRoute
} from './';

const ENTITY_STATES = [...sprawaRoute, ...sprawaPopupRoute];

@NgModule({
    imports: [CrmSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SprawaComponent, SprawaDetailComponent, SprawaUpdateComponent, SprawaDeleteDialogComponent, SprawaDeletePopupComponent],
    entryComponents: [SprawaComponent, SprawaUpdateComponent, SprawaDeleteDialogComponent, SprawaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CrmSprawaModule {}
