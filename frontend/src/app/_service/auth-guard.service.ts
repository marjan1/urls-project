import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';
import {TokenStorage} from "../_shared/token.storage";
import {PortalAdminComponent} from "../portal-admin/portal-admin.component";
import {CompanyAdminComponent} from "../company-admin/company-admin.component";
import {AdvocateComponent} from "../advocate/advocate.component";
import {ApprenticeComponent} from "../apprentice/apprentice.component";

@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {
  constructor(private tokenStorage: TokenStorage, private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    console.log("Eve me");
    let decodedToken = this.tokenStorage.getDecodedToken();
    let accessDenied: string = 'page-not-found';
    if (decodedToken) {
      console.log('decoded token 1');
      if (route.component === PortalAdminComponent &&
        decodedToken.roles.find(value => value.authority === 'ROLE_PORTAL_ADMINISTRATOR')) {
        console.log('decoded token 2');
        return true;
      } else if (route.component === CompanyAdminComponent &&
        decodedToken.roles.find(value => value.authority === 'ROLE_ADVOCATE_COMPANY_ADMINISTRATOR')) {
        console.log('decoded token 3');
        return true;
      } else if (route.component === AdvocateComponent &&
        decodedToken.roles.find(value => value.authority === 'ROLE_ADVOCATE')) {
        return true;
      } else if (route.component === ApprenticeComponent &&
        decodedToken.roles.find(value => value.authority === 'ROLE_APPRENTICE')) {
        return true;
      } else {
        console.log('decoded token Access Denied');
        this.router.navigate([accessDenied]);
      }

    } else {
      console.log('user not logged in');
      this.router.navigate([accessDenied]);
    }
  }

  canActivateChild(route: ActivatedRouteSnapshot,
                   state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.canActivate(route, state);
  }
}
