import {ActivatedRouteSnapshot, CanActivate, CanActivateChild, Router, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {Injectable} from '@angular/core';
import {TokenStorage} from "../_shared/token.storage";
import {PortalAdminComponent} from "../portal-admin/portal-admin.component";
import {CompanyAdminComponent} from "../company-admin/company-admin.component";
import {AdvocateComponent} from "../advocate/advocate.component";
import {ApprenticeComponent} from "../apprentice/apprentice.component";
import {AuthService} from "./auth.service";
import {User} from "../_model/user.model";

@Injectable()
export class AuthGuard implements CanActivate, CanActivateChild {

  public loggedUser: User = null;

  constructor(private tokenStorage: TokenStorage, private router: Router,
              private authService: AuthService) {
  }


  canActivate(route: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    console.log("Guard can activateMethod");
    let user = this.authService.getLoggedUser();
    //  let decodedToken = this.tokenStorage.getDecodedToken();
    let accessDenied: string = 'page-not-found';
    if (user) {
      console.log('decoded token 1');
      if (route.component === PortalAdminComponent &&
        user.roleDtos.find(value => value.name === 'ROLE_PORTAL_ADMINISTRATOR')) {
        console.log('decoded token 2');
        return true;
      } else if (route.component === CompanyAdminComponent &&
        user.roleDtos.find(value => value.name === 'ROLE_ADVOCATE_COMPANY_ADMINISTRATOR')) {
        console.log('decoded token 3');
        return true;
      } else if (route.component === AdvocateComponent &&
        user.roleDtos.find(value => value.name === 'ROLE_ADVOCATE')) {
        return true;
      } else if (route.component === ApprenticeComponent &&
        user.roleDtos.find(value => value.name === 'ROLE_APPRENTICE')) {
        return true;
      } else {
        console.log('decoded token Access Denied');
        this.router.navigate([accessDenied]);
      }

    } else {
      console.log('user not logged in');
     this.authService.logout();
    }
  }

  canActivateChild(route: ActivatedRouteSnapshot,
                   state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    return this.canActivate(route, state);
  }


}
