import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {User} from "../_model/user.model";
import {CUser} from "../_model/cuser.model";


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

  editAdmin(admin : CUser): Observable<User> {
    console.log('Edit company admin  ', admin);
    return this.http.post<User>(this.baseUrl + '/user/edit',
      admin);
  }

  getAdminsPage( filter = '', sortOrder = 'asc',
                  pageNumber = 0, pageSize = 3): Observable<User[]>{
    console.log('get users page with number {} and size {}  ', pageNumber);
    let params = new HttpParams();

    // Begin assigning parameters
    params = params.append('pageNumber',pageNumber.toString());
    params = params.append('size', pageSize.toString());
    params = params.append('filter', filter);
    params = params.append('sortOrder', sortOrder);
    return this.http.get<User[]>(this.baseUrl + '/user/admins/page',{params : params});

    //    .pipe(map( rez => rez['content']));
  }


}
