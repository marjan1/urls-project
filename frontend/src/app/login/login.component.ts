import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material';
// import {AuthService} from '../core/auth.service';
import {TokenStorage} from '../_shared/token.storage';
import {AuthService} from "../_service/auth.service";
import {LoggedUser} from "../_model/logged-user.model";

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
      (data : string )=> {
        this.token.saveToken(data);
        let  loggedUser : LoggedUser  = this.token.decodedToken;

        if(loggedUser.roles.find(value => value.authority === 'ROLE_PORTAL_ADMINISTRATOR')) {
          this.router.navigate(['portal-admin']);
        }else if(loggedUser.roles.find(value => value.authority === 'ROLE_ADVOCATE_COMPANY_ADMINISTRATOR')) {
          this.router.navigate(['company-admin']);
        }else if(loggedUser.roles.find(value => value.authority === 'ROLE_ADVOCATE')) {
          this.router.navigate(['advocate']);
        } else if(loggedUser.roles.find(value => value.authority === 'ROLE_APPRENTICE')) {
          this.router.navigate(['apprentice']);
        }
      }
    );
  }

}
