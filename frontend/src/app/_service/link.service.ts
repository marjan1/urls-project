import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {User} from "../_model/user.model";
import {AddLink} from "../_model/add-link.model";
import {Link} from "../_model/link.model";


@Injectable()
export class LinkService {

  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {
  }

  checkEmailExistence(email: string): Observable<boolean> {
    console.log('check email existence ', email);
    return this.http.post<boolean>(this.baseUrl + '/app/emailcheck', email);
  }


  signUpNewUser(user: User): Observable<User> {
    console.log('signup nw user  ', user);
    return this.http.post<User>(this.baseUrl + '/signup', user);
  }

  addNewLink(addLink: AddLink): Observable<Link> {
    console.log('add new link  ', addLink);
    return this.http.post<Link>(this.baseUrl + '/link/add', addLink);
  }

  getSuggestionsForLink(link:string): Observable<any>{
    return this.http.get(this.baseUrl + '/link/suggestions', {
      params: {
        link: link
      }
    });
  }


}
