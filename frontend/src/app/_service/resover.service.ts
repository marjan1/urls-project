import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {AuthService} from "./auth.service";
import {Observable} from "rxjs";


@Injectable()
export class ResolverService implements Resolve<any> {
  constructor(private authService: AuthService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> {
   this.authService.currentUserSubject.next( this.authService.getLoggedUser());
   return this.authService.currentUserSubject.asObservable();
  }
}
