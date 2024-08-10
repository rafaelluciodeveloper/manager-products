import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../auth/services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  const expectedRoles = route.data['roles'] as Array<string>;
  const token = authService.getToken();

  if (!token) {
    router.navigate(['/auth/login']);
    return false;
  }

  const userHasRequiredRole = authService.hasAnyRole(expectedRoles);

  if (!userHasRequiredRole) {
    router.navigate(['/auth/unauthorized']);
    return false;
  }

  return true;
};
