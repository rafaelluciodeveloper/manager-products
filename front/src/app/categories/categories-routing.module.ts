import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {CategoryManagementComponent} from "./category-management/category-management.component";
import {authGuard} from "../guards/auth.guard";
import {CategoryFormComponent} from "./category-form/category-form.component";

const routes: Routes = [
  {
    path: '',
    component: CategoryManagementComponent,
    canActivate: [authGuard],
    data: { roles: ['ROLE_VIEW', 'ROLE_EDIT'] }
  },
  {
    path: 'create',
    component: CategoryFormComponent,
    canActivate: [authGuard],
    data: { roles: ['ROLE_EDIT'] }
  },
  {
    path: 'edit/:id',
    component: CategoryFormComponent,
    canActivate: [authGuard],
    data: { roles: ['ROLE_EDIT'] }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CategoriesRoutingModule { }
