import {Component, OnInit} from '@angular/core';
import {AppService} from "../_service/app.service";

@Component({
  selector: 'app-portal-admin',
  templateUrl: './init.component.html',
  styleUrls: ['./init.component.css']
})
export class InitComponent implements OnInit {

  displayedColumns = ['id', 'name', 'description'];

  constructor(private appService : AppService) { }

  ngOnInit() {

  }

}
