import {Component, OnInit} from '@angular/core';
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

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.css']
})
export class CompanyComponent implements OnInit {

  companyForm: FormGroup;
  company: AdvocateCompany;
  statuses: Status[];
  loggedUser: LoggedUser;
  user: User;

  constructor(private appService: AppService,
              private companyService: CompanyService,
              private userService: UserService,
              private snackBar: MatSnackBar,
              private authService: AuthService) {


  }

  ngOnInit() {

    console.log('Rolesss =  ', this.appService.getRoles1());
    console.log('StST = ', this.appService.getStauses1());

    this.statuses = this.appService.getStauses1();

    this.user = this.authService.getLoggedUser();
    this.company = this.user.advocateCompany;

    // this.user = this.authService.loggedUser.user;

    let statusEdit: Status;
    statusEdit = this.statuses.find(value => value.id === this.company.status.id);

    this.companyForm = new FormGroup({
      'name': new FormControl(this.company.name, Validators.required),
      'address': new FormControl(this.company.address),
      'companyPhone': new FormControl(this.company.phone),
      'companyEmail': new FormControl(this.company.email, Validators.required),
      'embs': new FormControl(this.company.embs),
      'edbs': new FormControl(this.company.edbs),
      'license': new FormControl(this.company.license),
      'companyType': new FormControl(this.company.type.toString() === "SOLO" ?
        CompanyType.SOLO : CompanyType.OFFICE, Validators.required),
      'cstatus': new FormControl(statusEdit, Validators.required)
    });

  }

  validateCompanyForm() {

    if (this.companyForm.valid) {
      this.company.name = this.companyForm.get('name').value;
      this.company.address = this.companyForm.get('address').value;
      this.company.phone = this.companyForm.get('companyPhone').value;
      this.company.email = this.companyForm.get('companyEmail').value;
      this.company.embs = this.companyForm.get('embs').value;
      this.company.edbs = this.companyForm.get('edbs').value;
      this.company.license = this.companyForm.get('license').value;
      this.company.status = this.companyForm.get('cstatus').value;
      if (this.companyForm.get('companyType').value === '0') {
        this.company.type = CompanyType.OFFICE;
      } else {
        this.company.type = CompanyType.SOLO;
      }
      this.companyService.editAdvocateCompany(this.company).subscribe(
        (editedCompany: AdvocateCompany) => {
          console.log('Edited Company', editedCompany);
          this.snackBar.open('Company successfully edited', null, {
            duration: 5000,
          });
        }, (error: any) => {
          console.error("Error while signing in new User", error);
          this.snackBar.open('Error while editing company ', null, {
            duration: 5000,
          });
        });
    } else {
      this.snackBar.open('Entered data is not valid', null, {
        duration: 5000,
      });
    }

  }


}
