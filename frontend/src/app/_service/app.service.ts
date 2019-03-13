import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {Status} from "../_model/status.model";
import {TokenStorage} from "../_shared/token.storage";
import {Role} from "../_model/role.model";

@Injectable()
export class AppService {

  private baseUrl = 'http://localhost:8080/api';
  private statuses: Status[] = null;
  private roles: Role[] = null;


  constructor(private http: HttpClient, private tokenStorage: TokenStorage) {
  }

  public load(){
    this.initStatuses();
    this.initRoles();
  }

  public getStauses1():Status[]{
    return this.statuses;
  }

  public getRoles1():Role[]{
    return this.roles;
  }

  initStatuses(){
    console.log('Init Statuses at Start Up');
    return new Promise((resolve, reject) => {
      this.http
        .get(this.baseUrl + '/app/statuses')
        .subscribe( (response:Status[]) => {
          this.statuses = response;
          resolve(true);
          console.log('Init Statuses are taken');
        })
    })
  }

  initRoles(){
    console.log('Init Roles at Start Up');
    return new Promise((resolve, reject) => {
      this.http
        .get(this.baseUrl + '/app/roles')
        .subscribe( (response:Role[]) => {
          this.roles = response;
          resolve(true);
          console.log('Init Roles are taken');
        })
    })
  }

  public getStatuses(): Observable<Status[]> {
    return this.http.get<Status[]>(this.baseUrl + '/app/statuses');
  }

  public getRoles(): Observable<Role[]> {
    return this.http.get<Status[]>(this.baseUrl + '/app/roles');
  }

  public isLoggedIn(): boolean {
    //return this.tokenStorage.getToken() ? true : false;
    return !!this.tokenStorage.getToken();
  }

}
