import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Status} from "../../_model/status.model";
import {Role} from "../../_model/role.model";
import {AppService} from "../../_service/app.service";
import {MatSnackBar} from "@angular/material";
import {User} from "../../_model/user.model";
import {Observable} from "rxjs";
import {AdvocateCompany} from "../../_model/advocate-company.model";
import {CompanyType} from "../../_model/company-type.model";
import {CompanyService} from "../../_service/company.service";
import {UserService} from "../../_service/user.service";
import {CompanyUser} from "../../_model/company-user.model";

@Component({
  selector: 'app-new-company',
  templateUrl: './new-company.component.html',
  styleUrls: ['./new-company.component.css']
})
export class NewCompanyComponent implements OnInit {


  hide = true;
  newCompanyForm: FormGroup;
  newCompanyAdminForm: FormGroup;
  statuses: Status[];
  roles: Role[];
  user: User;
  company: AdvocateCompany;
  showAdminForm: boolean;

  constructor(private appService: AppService,
              private companyService: CompanyService,
              private userService: UserService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.showAdminForm = false;
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

    this.newCompanyForm = new FormGroup({
      'name': new FormControl('', Validators.required),
      'address': new FormControl(''),
      'companyPhone': new FormControl(''),
      'companyEmail': new FormControl('', Validators.required),
      'embs': new FormControl(''),
      'edbs': new FormControl(''),
      'license': new FormControl(''),
      'companyType': new FormControl('', Validators.required),
      'cstatus': new FormControl('', Validators.required)
    });

    this.newCompanyAdminForm = new FormGroup({
      'firstname': new FormControl('', Validators.required),
      'lastname': new FormControl(''),
      'email': new FormControl('', [Validators.required, Validators.email],
        this.forbiddenEmails.bind(this)),
      'password': new FormControl('', Validators.required),
      'phone': new FormControl('', Validators.required),
      'uroles': new FormControl('', Validators.required),
      'ustatus': new FormControl('', Validators.required)
    });
  }

  validateCompanyForm() {

    if (this.newCompanyForm.valid) {
      this.company = new AdvocateCompany();
      this.company.name = this.newCompanyForm.get('name').value;
      this.company.address = this.newCompanyForm.get('address').value;
      this.company.phone = this.newCompanyForm.get('companyPhone').value;
      this.company.email = this.newCompanyForm.get('companyEmail').value;
      this.company.embs = this.newCompanyForm.get('embs').value;
      this.company.edbs = this.newCompanyForm.get('edbs').value;
      this.company.license = this.newCompanyForm.get('license').value;
      this.company.status = this.newCompanyForm.get('cstatus').value;
      if (this.newCompanyForm.get('companyType').value === '0') {
        this.company.type = CompanyType.OFFICE;
      } else {
        this.company.type = CompanyType.SOLO;
      }
      this.showAdminForm = true;
    } else {
      this.showAdminForm = false;
    }

  }

  addCompanyWIthAdmin() {
    console.log("Admin form valid  ", this.newCompanyAdminForm.valid);
    if (this.newCompanyAdminForm.valid) {
      console.log('Valid forms');

      this.user = new User();
      this.user.name = this.newCompanyAdminForm.get('firstname').value;
      this.user.surname = this.newCompanyAdminForm.get('lastname').value;
      this.user.email = this.newCompanyAdminForm.get('email').value;
      this.user.password = this.newCompanyAdminForm.get('password').value;
      this.user.phone = this.newCompanyAdminForm.get('phone').value;

      this.user.accountGroupLevel = 1;


      let companyWithAdmin: CompanyUser = new CompanyUser();
      companyWithAdmin.advocateCompany = this.company;
      companyWithAdmin.user = this.user;


      this.companyService.addNewCompanyWithAdmin(companyWithAdmin).subscribe(
        (company: AdvocateCompany) => {
          console.log("Company is added  ", company.name);
          this.snackBar.open('User succesfully signed in', null, {
            duration: 5000,
          });
          this.newCompanyForm.reset(true);
          this.newCompanyAdminForm.reset(true);
          this.showAdminForm = false;

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
      console.log(this.newCompanyForm.get('email'));
      this.userService.checkEmailExistence(this.newCompanyAdminForm.get('email').value).subscribe(
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
