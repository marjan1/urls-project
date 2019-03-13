import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {LoggedUser} from "../_model/logged-user.model";
import {BehaviorSubject} from "rxjs";
import {TokenStorage} from "../_shared/token.storage";
import {CurrentUser} from "../_model/current-user.model";

@Injectable()
export class AuthService {

  baseUrl: 'http://localhost:8080/email2sms/';

  public currentUser: Observable<LoggedUser>;
  private currentUserSubject: BehaviorSubject<LoggedUser>;

  constructor(private http: HttpClient, private tokenStorage: TokenStorage) {
    this.currentUserSubject = new BehaviorSubject<LoggedUser>(tokenStorage.getDecodedToken());
    this.currentUser = this.currentUserSubject.asObservable();
  }


  attemptAuth(ussername: string, password: string): Observable<CurrentUser> {
    const credentials = {email: ussername, password: password};
    console.log('attempAuth ::');
    return this.http.post<CurrentUser>('http://localhost:8080/login ', credentials,)
      ;
    //return this.http.post('http://localhost:8080/login ', credentials,{ responseType: 'text' });
  }

}
