import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {TokenStorage} from "../_shared/token.storage";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  isLoggedIn : boolean;
  constructor(private tokenStorage : TokenStorage, private router: Router ) { }


  ngOnInit() {
    this.isLoggedIn = !!this.tokenStorage.getToken();
  }

  showSignin(){
    this.router.navigate(['/login']);
  }

  logout(){
    this.tokenStorage.signOut();
    this.router.navigate(['/login']);
  }

  showSignup(){
    this.router.navigate(['/signup']);
  }

}
