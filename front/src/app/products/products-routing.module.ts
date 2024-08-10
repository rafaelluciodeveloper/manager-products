import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ProductManagementComponent} from "./product-management/product-management.component";
import {ProductFormComponent} from "./product-form/product-form.component";
import {authGuard} from "../guards/auth.guard";

const routes: Routes = [
  {
    path: '',
    component: ProductManagementComponent,
    canActivate: [authGuard],
    data: { roles: ['ROLE_VIEW', 'ROLE_EDIT'] }
  },
  {
    path: 'create',
    component: ProductFormComponent,
    canActivate: [authGuard],
    data: { roles: ['ROLE_EDIT'] }
  },
  {
    path: 'edit/:id',
    component: ProductFormComponent,
    canActivate: [authGuard],
    data: { roles: ['ROLE_EDIT'] }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ProductsRoutingModule { }
