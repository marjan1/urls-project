import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AppService} from "../../_service/app.service";
import {Status} from "../../_model/status.model";
import {Role} from "../../_model/role.model";
import {ConfirmPasswordValidator} from "../../_shared/validator/confirm-password.validator";
import {Observable} from "rxjs";
import {UserService} from "../../_service/user.service";
import {User} from "../../_model/user.model";
import {MatSnackBar} from "@angular/material";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  hide = true;
  signupForm: FormGroup;
  statuses: Status[];
  roles: Role[];

  constructor(private appService: AppService,
              private userService: UserService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.appService.getStatuses().subscribe(
      (data: Status[]) => {
        this.statuses = data;
      }
    );

    this.appService.getRoles().subscribe(
      (data: Role[]) => {
        this.roles = data;
      }
    );

    this.signupForm = new FormGroup({
      'firstname': new FormControl('', Validators.required),
      'lastname': new FormControl(''),
      'email': new FormControl('', [Validators.required, Validators.email],
        this.forbiddenEmails.bind(this)),
      'password': new FormControl('', Validators.required),
      'confirmPassword': new FormControl('', Validators.required),
      'username': new FormControl('', Validators.required)
    }, [
      ConfirmPasswordValidator.MatchPassword
    ]);
  }

  register() {
    console.log("Form  ", this.signupForm.value);
    if (this.signupForm.valid) {
      let user: User = new User();
      user.name = this.signupForm.get('firstname').value;
      user.surname = this.signupForm.get('lastname').value;
      user.email = this.signupForm.get('email').value;
      user.password = this.signupForm.get('password').value;
      user.matchingPassword = this.signupForm.get('confirmPassword').value;
      user.phone = this.signupForm.get('username').value;

      this.userService.signUpNewUser(user).subscribe(
        (user: User) => {
          console.log("User succesfully signed in ", user);
          this.snackBar.open('User succesfully signed in', null, {
            duration: 5000,
          });

        }, (error: any) => {
          console.error("Error while signing in new User", error);
          this.snackBar.open('Error while signing in new User', null, {
            duration: 5000,
          });
        }
      )
    }
  }

  forbiddenEmails(control: FormControl): Promise<any> | Observable<any> {
    const promise = new Promise<any>((resolve, reject) => {
      console.log(this.signupForm.get('email'));
      this.userService.checkEmailExistence(this.signupForm.get('email').value).subscribe(
        (emailExist: boolean) => {
          console.log('Returned')
          if (emailExist) {
            resolve({'emailIsForbidden': true});

          } else {
            resolve(null);
          }
        }
      );

    });
    return promise;
  }


}
