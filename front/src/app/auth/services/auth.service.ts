import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/auth';

  constructor(private http: HttpClient) { }

  login(credentials: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/login`, credentials);
  }

  register(user: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/register`, user);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }

  getUserRoles(): string[] {
    const token = this.getToken();
    if (token) {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.roles || [];
    }
    return [];
  }

  hasRole(role: string): boolean {
    return this.getUserRoles().includes(role);
  }

  hasAnyRole(roles: string[]): boolean {
    return roles.some(role => this.hasRole(role));
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  getUsername(): string | null {
    const token = this.getToken();
    if (token) {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.sub;
    }
    return null;
  }
}
