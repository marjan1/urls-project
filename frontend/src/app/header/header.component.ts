import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../_service/auth.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isLoggedIn : boolean;
  constructor(private authService: AuthService, private router: Router ) { }


  ngOnInit() {
    this.authService.isuserLogged$.subscribe(
      value => this.isLoggedIn = value
    )

  }

  showSignin(){
    this.router.navigate(['/login']);
  }

  logout(){
    this.authService.logout();
  }

  showSignup(){
    this.router.navigate(['/signup']);
  }

}
