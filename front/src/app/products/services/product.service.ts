import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/api/products';

  constructor(private http: HttpClient) { }

  getProducts(page: number, size: number, filter: any): Observable<any> {
    let params = new HttpParams();
    params = params.append('page', page.toString());
    params = params.append('size', size.toString());

    if (filter.code) {
      params = params.append('code', filter.code);
    }
    if (filter.description) {
      params = params.append('description', filter.description);
    }
    if (filter.minPrice) {
      params = params.append('minPrice', filter.minPrice.toString());
    }
    if (filter.maxPrice) {
      params = params.append('maxPrice', filter.maxPrice.toString());
    }
    if (filter.categoryId) {
      params = params.append('categoryId', filter.categoryId.toString());
    }

    return this.http.get(this.apiUrl, { params });
  }

  saveProduct(product: any): Observable<any> {
    return this.http.post(this.apiUrl, product);
  }

  deleteProduct(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  getProductById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }

  updateProduct(id: number, product: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/${id}`, product);
  }
}
