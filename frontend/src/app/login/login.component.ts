import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material';
// import {AuthService} from '../core/auth.service';
import {TokenStorage} from '../_shared/token.storage';
import {AuthService} from "../_service/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  username: string;
  password: string;

  constructor(private router: Router, public dialog: MatDialog, private authService: AuthService, private token: TokenStorage) {
  }

  login(): void {
    this.authService.attemptAuth(this.username, this.password).subscribe(
      (data : string )=> {
        //this.token.saveToken(data.split(' ')[1]);
        this.token.saveToken(data);
        this.router.navigate(['portal-admin']);
      }
    );
  }

}
