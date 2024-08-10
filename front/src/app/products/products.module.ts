import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductsRoutingModule } from './products-routing.module';
import { ProductManagementComponent } from './product-management/product-management.component';
import { ProductFormComponent } from './product-form/product-form.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {NgxMaskDirective} from "ngx-mask";


@NgModule({
  declarations: [
    ProductManagementComponent,
    ProductFormComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ProductsRoutingModule,
    NgxMaskDirective
  ]
})
export class ProductsModule { }
