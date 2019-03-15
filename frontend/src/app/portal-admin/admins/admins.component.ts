import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSnackBar, MatSort} from "@angular/material";
import {debounceTime, distinctUntilChanged, tap} from "rxjs/operators";
import {fromEvent, merge, Observable} from 'rxjs';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AppService} from "../../_service/app.service";
import {Status} from "../../_model/status.model";
import {AdminDatasource} from "../../_shared/datasource/admin.datasource";
import {UserService} from "../../_service/user.service";
import {CUser} from "../../_model/cuser.model";

@Component({
  selector: 'app-admins',
  templateUrl: './admins.component.html',
  styleUrls: ['./admins.component.css']
})
export class AdminsComponent implements OnInit {

  displayedColumns: string[] = ['name', 'lastname', 'email',
    'phone', 'status', 'edit'];

  dataSource: AdminDatasource;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  @ViewChild(MatSort) sort: MatSort;

  @ViewChild('input') input: ElementRef;

  editAdminForm: FormGroup;
  editAdminMode: boolean;
  statuses: Status[];
  user: CUser;
  editAdminId: number;

  constructor(private appService: AppService,
              private userService: UserService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.dataSource = new AdminDatasource(this.userService);
    this.dataSource.loadAdmins(null, 'asc', 0, 3);
    this.editAdminMode = false;

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

          this.loadAdminsPage();
        })
      )
      .subscribe();

    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        tap(() => this.loadAdminsPage())
      )
      .subscribe();
  }

  editElement(element: CUser) {
    console.log('Edit element : ', element);
    this.editAdminMode = true;

    this.user = element;
    let statusEdit: Status;
    statusEdit = this.statuses.find(value => value.id === element.status.id);

    console.log('Element status = ', this.statuses[0]);
    this.editAdminForm = new FormGroup({
      'firstname': new FormControl(element.name, Validators.required),
      'lastname': new FormControl(element.surname),
      'email': new FormControl(element.email, [Validators.required, Validators.email]),
      'phone': new FormControl(element.phone, Validators.required),
      'ustatus': new FormControl(statusEdit, Validators.required)
    });
  }


  editAdmin() {
    if (this.editAdminForm.valid) {


      this.user.name = this.editAdminForm.get('firstname').value;
      this.user.surname = this.editAdminForm.get('lastname').value;
      this.user.email = this.editAdminForm.get('email').value;
      this.user.phone = this.editAdminForm.get('phone').value;
      this.user.status = this.editAdminForm.get('ustatus').value;
      this.user.accountGroupLevel = 1;


      this.userService.editAdmin(this.user).subscribe(
        (editedUser: CUser) => {
          console.log('Edited admin', editedUser);
          this.loadAdminsPage();
          this.editAdminMode = false;
          this.snackBar.open('Admin successfully edited', null, {
            duration: 5000,
          });
        }, (error: any) => {
          console.error("Error while editing admin in new User", error);
          this.snackBar.open('Error while editing admin ', null, {
            duration: 5000,
          });
        });

    }
  }

  loadAdminsPage() {
    this.dataSource.loadAdmins(
      this.input.nativeElement.value,
      this.sort.direction,
      this.paginator.pageIndex,
      this.paginator.pageSize);
  }

  forbiddenEmailsForUser(control: FormControl): Promise<any> | Observable<any> {
    const promise = new Promise<any>((resolve, reject) => {
      console.log(this.editAdminForm.get('email'));
      this.userService.checkEmailExistence(this.editAdminForm.get('email').value).subscribe(
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
