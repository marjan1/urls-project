import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AppService} from "../../_service/app.service";
import {MatSnackBar} from "@angular/material";
import {User} from "../../_model/user.model";
import {LinkService} from "../../_service/link.service";
import {AddLink} from "../../_model/add-link.model";
import {Link} from "../../_model/link.model";
import {MatSnackBarConfig, MatSnackBarHorizontalPosition} from "@angular/material/snack-bar";

@Component({
  selector: 'app-new-company',
  templateUrl: './new-link.component.html',
  styleUrls: ['./new-link.css']
})
export class NewLinkComponent implements OnInit {


  hide = true;
  newCompanyForm: FormGroup;
  user: User;
  addLink: AddLink;
  links: Link[];
  suggestioTags: string[];
  showAdminForm: boolean;

  message: string = 'Link added successfully';
  actionButtonLabel: string = 'Close';
  action: boolean = true;
  setAutoHide: boolean = true;
  autoHide: number = 7000;
  horizontalPosition: MatSnackBarHorizontalPosition = 'center';

  constructor(private appService: AppService,
              private linkService: LinkService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    this.showAdminForm = false;
    this.links = [];
    this.suggestioTags = [];

    this.newCompanyForm = new FormGroup({
      'url': new FormControl('', Validators.required),
      'tags': new FormControl('', Validators.required),

    });


  }

  addNewLinkSubmit() {
    this.addLink = new AddLink();
    this.addLink.link = this.newCompanyForm.get('url').value;
    this.addLink.tags = this.newCompanyForm.get('tags').value.split(" ");
    let config = new MatSnackBarConfig();

    if (this.addLink.link && this.addLink.link.length > 0
      &&  this.newCompanyForm.get('tags').value && this.newCompanyForm.get('tags').value.length > 0) {
      this.linkService.addNewLink(this.addLink).subscribe(
        (link: Link) => {
          this.links.push(link);
          config.horizontalPosition = this.horizontalPosition;
          config.duration = this.setAutoHide ? this.autoHide : 0;
          this.snackBar.open(this.message, this.action ? this.actionButtonLabel : undefined, config);
        }
      )
    }else{
      this.snackBar.open("Fields should not be empty ", this.action ? this.actionButtonLabel : undefined, config);
    }
  }

  getSuggestions() {
    console.log("Eve ima  blur");
    this.linkService.getSuggestionsForLink(this.newCompanyForm.get('url').value).subscribe(
      (value: any) => {
        this.suggestioTags = value;
      }
    )
  }


}
