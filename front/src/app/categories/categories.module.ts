import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CategoriesRoutingModule } from './categories-routing.module';
import { CategoryManagementComponent } from './category-management/category-management.component';
import { CategoryFormComponent } from './category-form/category-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    CategoryManagementComponent,
    CategoryFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    CategoriesRoutingModule
  ]
})
export class CategoriesModule { }
