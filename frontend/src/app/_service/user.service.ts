import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {User} from "../_model/user.model";


@Injectable()
export class UserService {

  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {
  }

  checkEmailExistence(email: string): Observable<boolean> {
    console.log('check email existence ', email);
    return this.http.post<boolean>(this.baseUrl + '/app/emailcheck', email);
  }

  signUpNewUser(user: User): Observable<User> {
    console.log('signup nw user  ', user);
    return this.http.post<User>(this.baseUrl + '/app/add', [user, user]);
  }

}
