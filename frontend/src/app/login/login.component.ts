import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material';
// import {AuthService} from '../core/auth.service';
import {TokenStorage} from '../_shared/token.storage';
import {AuthService} from "../_service/auth.service";
import {CurrentUser} from "../_model/current-user.model";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  username: string;
  password: string;

  constructor(private router: Router,

              public dialog: MatDialog, private authService: AuthService,
              private token: TokenStorage) {
  }

  login(): void {
    this.authService.attemptAuth(this.username, this.password).subscribe(
      (data : CurrentUser )=> {
        console.log(data);
        this.authService.loggedUser = data['user'];
        this.authService.isLoggedUserSubject.next(true);
        //
         this.token.saveToken(data['bearerToken']);
   //     let  loggedUser : LoggedUser  = this.token.decodedToken;

 this.router.navigate(['portal-admin']);

      }
    );
  }

}
