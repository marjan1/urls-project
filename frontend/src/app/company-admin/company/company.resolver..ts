import {Component, Injectable, OnInit} from '@angular/core';
import {AdvocateCompany} from "../../_model/advocate-company.model";
import {CompanyType} from "../../_model/company-type.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Status} from "../../_model/status.model";
import {AppService} from "../../_service/app.service";
import {CompanyService} from "../../_service/company.service";
import {MatSnackBar} from "@angular/material";
import {AuthService} from "../../_service/auth.service";
import {UserService} from "../../_service/user.service";
import {LoggedUser} from "../../_model/logged-user.model";
import {User} from "../../_model/user.model";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs";


interface IReturn {
  user: User;
}

@Injectable()
export class CompanyResolver implements Resolve<User> {



  constructor(private appService: AppService,
              private companyService: CompanyService,
              private userService: UserService,
              private snackBar: MatSnackBar,
              private authService: AuthService) {


  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<User> | Promise<User> | User {
    console.log("At resolver");
    return this.userService.getUserByUsername('marr@co.cv');
  }







}
