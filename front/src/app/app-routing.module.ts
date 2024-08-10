import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LayoutComponent} from "./layout/layout-component";
import {authGuard} from "./guards/auth.guard";

const routes: Routes = [
  { path: 'auth', loadChildren: () => import('./auth/auth.module').then(m => m.AuthModule) },
  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard],
    data: { roles: ['ROLE_VIEW', 'ROLE_EDIT'] },
    children: [
      { path: 'products', loadChildren: () => import('./products/products.module').then(m => m.ProductsModule) },
      { path: 'categories', loadChildren: () => import('./categories/categories.module').then(m => m.CategoriesModule) }
    ]
  },
  { path: '**', redirectTo: '/auth/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
