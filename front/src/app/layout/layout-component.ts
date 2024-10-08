import { Component } from '@angular/core';
import { Router } from '@angular/router';
import {AuthService} from "../auth/services/auth.service";

@Component({
  selector: 'app-layout',
  templateUrl: './layout-component.html',
  styleUrls: ['./layout-component.css']
})
export class LayoutComponent {

  constructor(public authService: AuthService, private router: Router) {}

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }
}
