import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CategoryService } from '../services/category.service';

@Component({
  selector: 'app-category-management',
  templateUrl: './category-management.component.html',
  styleUrl: './category-management.component.css'
})
export class CategoryManagementComponent implements OnInit {
  categories: any[] = [];
  page = 0;
  pageSize = 10;
  first =false;
  last = false;
  filter = {
    code: '',
    description: ''
  };

  constructor(private categoryService: CategoryService, private router: Router) {}

  ngOnInit() {
    this.getCategories();
  }

  getCategories() {
    this.categoryService.getCategories(this.page, this.pageSize, this.filter)
      .subscribe(data => this.categories = data.content);
  }

  onFilterChange() {
    this.getCategories();
  }

  createCategory() {
    this.router.navigate(['/categories/create']);
  }

  editCategory(category: any) {
    this.router.navigate(['/categories/edit', category.id]);
  }

  deleteCategory(id: number) {
    if (confirm('Are you sure you want to delete this category?')) {
      this.categoryService.deleteCategory(id).subscribe(() => {
        this.getCategories();
      });
    }
  }

  paginationNext() {
    if (!this.last) {
      this.page = this.page + 1;
      this.getCategories();
    }
  }

  paginationPrevious() {
    if (!this.first) {
      this.page = this.page - 1;
      this.getCategories();
    }
  }
}
