import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CategoryService } from '../services/category.service';

@Component({
  selector: 'app-category-form',
  templateUrl: './category-form.component.html',
  styleUrl: './category-form.component.css'
})
export class CategoryFormComponent implements OnInit {
  categoryForm: FormGroup;
  categoryId: number | null = null;
  isEditMode: boolean = false;

  constructor(
    private fb: FormBuilder,
    private categoryService: CategoryService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.categoryForm = this.fb.group({
      code: ['', [Validators.required, Validators.maxLength(50)]],
      description: ['', [Validators.required, Validators.maxLength(255)]]
    });
  }

  ngOnInit(): void {
    this.categoryId = this.route.snapshot.params['id'];
    if (this.categoryId) {
      this.isEditMode = true;
      this.categoryService.getCategoryById(this.categoryId).subscribe(category => {
        this.categoryForm.patchValue(category);
      });
    }
  }

  onSubmit(): void {
    if (this.categoryForm.invalid) {
      return;
    }

    if (this.isEditMode) {
      this.categoryService.updateCategory(this.categoryId!, this.categoryForm.value).subscribe(() => {
        this.router.navigate(['/categories']);
      });
    } else {
      this.categoryService.saveCategory(this.categoryForm.value).subscribe(() => {
        this.router.navigate(['/categories']);
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/categories']);
  }
}
