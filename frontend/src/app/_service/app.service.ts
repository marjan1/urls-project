import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {TokenStorage} from "../_shared/token.storage";

@Injectable()
export class AppService {

  private baseUrl = 'http://localhost:8080/api';


  constructor(private http: HttpClient, private tokenStorage: TokenStorage) {
  }

  public load() {

  }


  public isLoggedIn(): boolean {
    //return this.tokenStorage.getToken() ? true : false;
    return !!this.tokenStorage.getToken();
  }

}
