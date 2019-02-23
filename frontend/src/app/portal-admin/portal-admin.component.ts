import {Component, OnInit} from '@angular/core';
import {AppService} from "../_service/app.service";
import {MatTableDataSource} from "@angular/material";
import {Status} from "../_model/status.model";
import {CompanyType} from "../_model/company-type.model";

@Component({
  selector: 'app-portal-admin',
  templateUrl: './portal-admin.component.html',
  styleUrls: ['./portal-admin.component.css']
})
export class PortalAdminComponent implements OnInit {

  displayedColumns = ['id', 'name', 'description'];
  dataSource = new MatTableDataSource<Status>();
  companyTypes:CompanyType;

  constructor(private appService : AppService) { }

  ngOnInit() {
    this.appService.getStatuses().subscribe(
      data => {
        this.dataSource.data = data;
      }
    );
  }

}
