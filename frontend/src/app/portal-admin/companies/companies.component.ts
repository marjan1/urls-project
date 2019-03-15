import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSnackBar, MatSort} from "@angular/material";
import {CompanyService} from "../../_service/company.service";
import {CompanyDatasource} from "../../_shared/datasource/company.datasource";
import {debounceTime, distinctUntilChanged, tap} from "rxjs/operators";
import {fromEvent, merge} from 'rxjs';
import {AdvocateCompany} from "../../_model/advocate-company.model";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AppService} from "../../_service/app.service";
import {Status} from "../../_model/status.model";
import {CompanyType} from "../../_model/company-type.model";

@Component({
  selector: 'app-companies',
  templateUrl: './companies.component.html',
  styleUrls: ['./companies.component.css']
})
export class CompaniesComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['name', 'address', 'phone', 'email', 'embs'
    , 'edbs', 'type', 'status', 'edit'];

  dataSource: CompanyDatasource;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  @ViewChild(MatSort) sort: MatSort;

  @ViewChild('input') input: ElementRef;

  newCompanyForm: FormGroup;
  editCompanyMode: boolean;
  statuses: Status[];
  company: AdvocateCompany;
  editCompanyId:number;

  constructor(private appService: AppService,
              private companyService: CompanyService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.dataSource = new CompanyDatasource(this.companyService);
    this.dataSource.loadAdvocateCompanies(null, 'asc', 0, 3);
    this.editCompanyMode = false;

  }

  ngAfterViewInit() {
    this.appService.getStatuses().subscribe(
      (data: Status[]) => {
        this.statuses = data;
      }
    );

    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);

    fromEvent(this.input.nativeElement, 'keyup')
      .pipe(
        debounceTime(150),
        distinctUntilChanged(),
        tap(() => {
          this.paginator.pageIndex = 0;

          this.loadCompaniesPage();
        })
      )
      .subscribe();

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        tap(() => this.loadCompaniesPage())
      )
      .subscribe();
  }

  editElement(element: AdvocateCompany) {
    console.log('Cliknato: ', element);
    this.editCompanyMode = true;
    this.editCompanyId = element.id;
    let statusEdit : Status;
    statusEdit = this.statuses.find(value => value.id === element.status.id);

    console.log('Element status = ', this.statuses[0]);
    this.newCompanyForm = new FormGroup({
      'name': new FormControl(element.name, Validators.required),
      'address': new FormControl(element.address),
      'companyPhone': new FormControl(element.phone),
      'companyEmail': new FormControl(element.email, Validators.required),
      'embs': new FormControl(element.embs),
      'edbs': new FormControl(element.edbs),
      'license': new FormControl(element.license),
      'companyType': new FormControl(element.type.toString() === "SOLO" ?
        CompanyType.SOLO : CompanyType.OFFICE, Validators.required),
      'cstatus': new FormControl(statusEdit, Validators.required)
    });


  }

  editCompany() {
    if (this.newCompanyForm.valid) {

      this.company = new AdvocateCompany();
      this.company.id = this.editCompanyId;
      this.company.name = this.newCompanyForm.get('name').value;
      this.company.address = this.newCompanyForm.get('address').value;
      this.company.phone = this.newCompanyForm.get('companyPhone').value;
      this.company.email = this.newCompanyForm.get('companyEmail').value;
      this.company.embs = this.newCompanyForm.get('embs').value;
      this.company.edbs = this.newCompanyForm.get('edbs').value;
      this.company.license = this.newCompanyForm.get('license').value;
      this.company.status = this.newCompanyForm.get('cstatus').value;
      if (this.newCompanyForm.get('companyType').value === 0) {
        this.company.type = CompanyType.OFFICE;
      } else {
        this.company.type = CompanyType.SOLO;
      }



      this.companyService.editAdvocateCompany(this.company).subscribe(
        (editedCompany: AdvocateCompany) => {
          console.log('Edited Company', editedCompany);
          this.loadCompaniesPage();
          this.editCompanyMode = false;
          this.snackBar.open('Company successfully edited', null, {
            duration: 5000,
          });
        }, (error: any) => {
          console.error("Error while signing in new User", error);
          this.snackBar.open('Error while editing company ', null, {
            duration: 5000,
          });
        });

    }
  }

  loadCompaniesPage() {
    this.dataSource.loadAdvocateCompanies(
      this.input.nativeElement.value,
      this.sort.direction,
      this.paginator.pageIndex,
      this.paginator.pageSize);
  }


}
