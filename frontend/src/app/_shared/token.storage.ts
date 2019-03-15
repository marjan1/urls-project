import {Injectable} from '@angular/core';
import * as jwt_decode from "jwt-decode";
import {LoggedUser} from "../_model/logged-user.model";


const TOKEN_KEY = 'AuthToken';

@Injectable()
export class TokenStorage {

  decodedToken: LoggedUser;

  constructor() { }

  signOut() {
    sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.clear();
    this.decodedToken = null;
  }

  public saveToken(token: string) {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY,  token);
    this.decodedToken = jwt_decode(token);

  }

  public getDecodedToken():LoggedUser {
    if(!!this.getToken()) {
      return jwt_decode(this.getToken());
    }
    return null;
  }

  public getToken(): string {
    return sessionStorage.getItem(TOKEN_KEY);
  }


}
