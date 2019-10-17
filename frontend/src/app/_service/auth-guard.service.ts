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
      return true;
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
