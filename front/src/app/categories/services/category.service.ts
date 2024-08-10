import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private apiUrl = 'http://localhost:8080/api/categories';

  constructor(private http: HttpClient) { }

  getCategories(page: number, size: number, filter: any): Observable<any> {
    let params = new HttpParams();
    params = params.append('page', page.toString());
    params = params.append('size', size.toString());
    if (filter.code) {
      params = params.append('code', filter.code);
    }
    if (filter.description) {
      params = params.append('description', filter.description);
    }

    return this.http.get(this.apiUrl, { params });
  }

  saveCategory(category: any): Observable<any> {
    return this.http.post(this.apiUrl, category);
  }

  deleteCategory(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  getCategoryById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  updateCategory(id: number, category: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, category);
  }
}
