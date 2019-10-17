import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSnackBar, MatSort} from "@angular/material";
import {FormGroup} from "@angular/forms";

@Component({
  selector: 'app-companies',
  templateUrl: './companies.component.html',
  styleUrls: ['./companies.component.css']
})
export class CompaniesComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['name', 'address', 'phone', 'email', 'embs'
    , 'edbs', 'type', 'status', 'edit'];



  @ViewChild(MatPaginator) paginator: MatPaginator;

  @ViewChild(MatSort) sort: MatSort;

  @ViewChild('input') input: ElementRef;

  newCompanyForm: FormGroup;
  editCompanyMode: boolean;

  editCompanyId:number;

  constructor(
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {


    this.editCompanyMode = false;

  }

  ngAfterViewInit() {


  }




}
