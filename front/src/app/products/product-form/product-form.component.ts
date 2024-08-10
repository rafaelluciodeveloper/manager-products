import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ProductService } from '../services/product.service';
import { CategoryService } from '../../categories/services/category.service';
import {catchError, throwError} from "rxjs";


@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrl:'./product-form.component.css'
})
export class ProductFormComponent implements OnInit {
  productForm: FormGroup;
  productId: number | null = null;
  isEditMode: boolean = false;
  categories: any[] = [];
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.productForm = this.fb.group({
      code: ['', [Validators.required, Validators.maxLength(50)]],
      description: ['', [Validators.required, Validators.maxLength(255)]],
      price: ['', [Validators.required, Validators.min(0)]],
      categoryId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadCategories();
    this.productId = this.route.snapshot.params['id'];
    if (this.productId) {
      this.isEditMode = true;
      this.productService.getProductById(this.productId).subscribe(product => {
        this.productForm.patchValue(product);
        this.productForm.patchValue({
          categoryId: product.category.id
        });
      });
    }
  }

  loadCategories() {
    this.categoryService.getCategories(0, 100, {}).subscribe(data => {
      this.categories = data.content;
    });
  }

  onSubmit(): void {
    if (this.productForm.invalid) {
      return;
    }

    if (this.isEditMode) {
      this.productService.updateProduct(this.productId!, this.productForm.value)
        .pipe(
          catchError(error => {
            this.handleError(error);
            return throwError(error);
          })
        )
        .subscribe(() => {
          this.router.navigate(['/products']);
        });
    } else {
      this.productService.saveProduct(this.productForm.value)
        .pipe(
          catchError(error => {
            this.handleError(error);
            return throwError(error);
          })
        )
        .subscribe(() => {
          this.router.navigate(['/products']);
        });
    }
  }

  onCancel(): void {
    this.router.navigate(['/products']);
  }

  private handleError(error: any): void {
    if (error.error && typeof error.error === 'object') {
      const errors = error.error;
      this.errorMessage = Object.keys(errors)
        .map(key => `${key}: ${errors[key]}`)
        .join(', ');
    } else {
      this.errorMessage = 'An unexpected error occurred. Please try again later.';
    }
  }
}
