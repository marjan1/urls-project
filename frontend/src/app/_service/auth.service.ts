import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class AuthService {

  baseUrl: 'http://localhost:8080/email2sms/';

  constructor(private http: HttpClient) {
  }


  attemptAuth(ussername: string, password: string): Observable<any> {
    const credentials = {email: ussername, password: password};
    console.log('attempAuth ::');
    return this.http.post('http://localhost:8080/login ', credentials,{ responseType: 'text' });

  }

}
