import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject} from "rxjs";
import {TokenStorage} from "../_shared/token.storage";
import {CurrentUser} from "../_model/current-user.model";
import "rxjs-compat/add/operator/publishReplay";
import {User} from "../_model/user.model";
import {UserService} from "./user.service";
import {Router} from "@angular/router";

@Injectable()
export class AuthService {

  baseUrl: 'http://localhost:8080/email2sms/';

  public isLoggedUserSubject = new BehaviorSubject<boolean>(false);
  public  isuserLogged$ = this.isLoggedUserSubject.asObservable()
    .publishReplay(1).refCount();

  //public currentUser = this.isLoggedUserSubject.asObservable();
  // .publishReplay(1).refCount();
  public currentUser = new Observable<CurrentUser>();
  public loggedUser: User = null;
  public trl: string = 'Eve Init Test';

  constructor(private http: HttpClient,
              private tokenStorage: TokenStorage,
              private userService: UserService,
              private router: Router) {
    // this.isLoggedUserSubject = new BehaviorSubject<LoggedUser>(tokenStorage.getDecodedToken());
    // this.currentUser = this.isLoggedUserSubject.asObservable();
    // this.currentUser.subscribe(data => {
    //   this.loggedUser = data['user'];
    //   this.tokenStorage.saveToken(data['bearerToken']);
    // });
  }


  attemptAuth(ussername: string, password: string): Observable<CurrentUser> {

    const credentials = {email: ussername, password: password};
    console.log('attempAuth ::');

   return this.http.post<CurrentUser>('http://localhost:8080/login ', credentials);

  }

  logout(){
    this.tokenStorage.signOut();
    this.isLoggedUserSubject.next(false);
    this.router.navigate(['/login']);
  }

  getLoggedUser(): User {
    // if (this.loggedUser === null) {
    //   if(this.tokenStorage.getToken() === null){
    //     this.tokenStorage.signOut();
    //     this.router.navigate(['/login']);
    //   }
    //   let logUser: LoggedUser = this.tokenStorage.getDecodedToken();
    //
    //   this.userService.getUserByUsername(logUser.sub).subscribe(
    //     data => {
    //       this.loggedUser = data;
    //     }
    //   );
    //
    // }
    return this.loggedUser;
  }


}
