import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ProductService} from '../services/product.service';
import {CategoryService} from "../../categories/services/category.service";

@Component({
  selector: 'app-product-management',
  templateUrl: './product-management.component.html',
  styleUrl: './product-management.component.css'
})
export class ProductManagementComponent implements OnInit {
  products: any[] = [];
  categories: any[] = [];
  page = 0;
  pageSize = 10;
  last = false;
  first = false;
  filter = {
    code: '',
    description: '',
    minPrice: 0,
    maxPrice: 0,
    categoryId: null
  };

  constructor(private productService: ProductService, private categoryService: CategoryService, private router: Router) {
  }

  ngOnInit() {
    this.loadCategories();
    this.getProducts();
  }

  getProducts() {
    this.productService.getProducts(this.page, this.pageSize, this.filter)
      .subscribe(data => {
        this.products = data.content
        this.last = data.last;
        this.first = data.first;
      });
  }

  onFilterChange() {
    this.getProducts();
  }

  createProduct() {
    this.router.navigate(['/products/create']);
  }

  editProduct(product: any) {
    this.router.navigate(['/products/edit', product.id]);
  }

  deleteProduct(id: number) {
    if (confirm('Are you sure you want to delete this product?')) {
      this.productService.deleteProduct(id).subscribe(() => {
        this.getProducts();
      });
    }
  }

  loadCategories() {
    this.categoryService.getCategories(0, 100, {}).subscribe(data => {
      this.categories = data.content;
    });
  }

  paginationNext() {
    if (!this.last) {
      this.page = this.page + 1;
      this.getProducts();
    }
  }

  paginationPrevios() {
    if (!this.first) {
      this.page = this.page - 1;
      this.getProducts();
    }
  }
}
