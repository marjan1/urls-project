import {Component, OnInit} from '@angular/core';
import {AppService} from "../_service/app.service";
import {MatTableDataSource} from "@angular/material";
import {Status} from "../_model/status.model";

@Component({
  selector: 'app-portal-admin',
  templateUrl: './portal-admin.component.html',
  styleUrls: ['./portal-admin.component.css']
})
export class PortalAdminComponent implements OnInit {

  displayedColumns = ['id', 'name', 'description'];
  dataSource = new MatTableDataSource<Status>();

  constructor(private appService : AppService) { }

  ngOnInit() {
    this.appService.getStatuses().subscribe(
      data => {
        this.dataSource.data = data;
      }
    );
  }

}
