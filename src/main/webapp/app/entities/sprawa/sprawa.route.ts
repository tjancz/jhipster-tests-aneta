import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Sprawa } from 'app/shared/model/sprawa.model';
import { SprawaService } from './sprawa.service';
import { SprawaComponent } from './sprawa.component';
import { SprawaDetailComponent } from './sprawa-detail.component';
import { SprawaUpdateComponent } from './sprawa-update.component';
import { SprawaDeletePopupComponent } from './sprawa-delete-dialog.component';
import { ISprawa } from 'app/shared/model/sprawa.model';

@Injectable({ providedIn: 'root' })
export class SprawaResolve implements Resolve<ISprawa> {
    constructor(private service: SprawaService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((sprawa: HttpResponse<Sprawa>) => sprawa.body));
        }
        return of(new Sprawa());
    }
}

export const sprawaRoute: Routes = [
    {
        path: 'sprawa',
        component: SprawaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'crmApp.sprawa.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sprawa/:id/view',
        component: SprawaDetailComponent,
        resolve: {
            sprawa: SprawaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'crmApp.sprawa.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sprawa/new',
        component: SprawaUpdateComponent,
        resolve: {
            sprawa: SprawaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'crmApp.sprawa.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sprawa/:id/edit',
        component: SprawaUpdateComponent,
        resolve: {
            sprawa: SprawaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'crmApp.sprawa.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sprawaPopupRoute: Routes = [
    {
        path: 'sprawa/:id/delete',
        component: SprawaDeletePopupComponent,
        resolve: {
            sprawa: SprawaResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'crmApp.sprawa.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
