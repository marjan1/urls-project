import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {User} from "../user/user.model";

@Injectable()
export class UserService {

  private userUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {
  }

  public getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.userUrl + '/users');
  }

}
