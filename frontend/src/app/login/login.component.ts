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
      //  this.authService.currentUserSubject.next(data);
        //
         this.token.saveToken(data['bearerToken']);
   //     let  loggedUser : LoggedUser  = this.token.decodedToken;

        if(data['user'].roleDtos.find(value => value.name === 'ROLE_PORTAL_ADMINISTRATOR')) {
          this.router.navigate(['portal-admin']);
        }else if(data['user'].roleDtos.find(value => value.name === 'ROLE_ADVOCATE_COMPANY_ADMINISTRATOR')) {
          this.router.navigate(['company-admin']);
        }else if(data['user'].roleDtos.find(value => value.name === 'ROLE_ADVOCATE')) {
          this.router.navigate(['advocate']);
        } else if(data['user'].roleDtos.find(value => value.name === 'ROLE_APPRENTICE')) {
          this.router.navigate(['apprentice']);
        }
      }
    );
  }

}
