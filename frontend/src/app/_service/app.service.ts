import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {Status} from "../_model/status.model";
import {TokenStorage} from "../_shared/token.storage";
import {Role} from "../_model/role.model";

@Injectable()
export class AppService {

  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient, private tokenStorage: TokenStorage) {}

  public getStatuses(): Observable<Status[]> {
    return this.http.get<Status[]>(this.baseUrl + '/app/statuses');
  }

  public getRoles(): Observable<Role[]> {
    return this.http.get<Status[]>(this.baseUrl + '/app/roles');
  }

  public isLoggedIn():boolean{
    //return this.tokenStorage.getToken() ? true : false;
    return !!this.tokenStorage.getToken();
  }

}
