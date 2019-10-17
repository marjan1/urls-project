import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AppService} from "../../_service/app.service";
import {MatSnackBar} from "@angular/material";
import {User} from "../../_model/user.model";
import {LinkService} from "../../_service/link.service";
import {AddLink} from "../../_model/add-link.model";
import {Link} from "../../_model/link.model";

@Component({
  selector: 'app-new-company',
  templateUrl: './new-link.component.html',
  styleUrls: ['./new-link.css']
})
export class NewLinkComponent implements OnInit {


  hide = true;
  newCompanyForm: FormGroup;
  newCompanyAdminForm: FormGroup;
  user: User;
  addLink: AddLink;
  links: Link[];
  showAdminForm: boolean;

  constructor(private appService: AppService,
              private userService: LinkService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.showAdminForm = false;
    this.links = [];

    this.newCompanyForm = new FormGroup({
      'url': new FormControl('', Validators.required),
      'tags': new FormControl('', Validators.required),

    });


  }

  addNewLinkSubmit() {
    this.addLink = new AddLink();
    this.addLink.link = this.newCompanyForm.get('url').value;
    this.addLink.tags = this.newCompanyForm.get('tags').value.split(" ");

    this.userService.addNewLink(this.addLink).subscribe(
      (link: Link) => {
        this.links.push(link);
      }
    )


  }



}
