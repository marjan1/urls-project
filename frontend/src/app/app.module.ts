import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {UserComponent} from "./user/user.component";
import {ErrorDialogComponent} from "./_shared/error-dialog.component";
import {LoginComponent} from "./login/login.component";
import {AuthService} from "./_service/auth.service";
import {TokenStorage} from "./_shared/token.storage";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {Interceptor} from "./_shared/inteceptor";
import {UserService} from "./_service/user.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule} from "@angular/forms";
import {CustomMaterialModule} from "./_shared/material.module";
import {AppRoutingModule} from "./_shared/app.routing.module";
import {PortalAdminComponent} from './portal-admin/portal-admin.component';
import {CompanyAdminComponent} from './company-admin/company-admin.component';
import {AdvocateComponent} from './advocate/advocate.component';
import {ApprenticeComponent} from './apprentice/apprentice.component';
import {HeaderComponent} from './header/header.component';
import {AppService} from "./_service/app.service";

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    LoginComponent,
    ErrorDialogComponent,
    PortalAdminComponent,
    CompanyAdminComponent,
    AdvocateComponent,
    ApprenticeComponent,
    HeaderComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    CustomMaterialModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [ErrorDialogComponent, UserService, AuthService, TokenStorage, TokenStorage,
    AppService,
    {provide: HTTP_INTERCEPTORS,
      useClass: Interceptor,
      multi : true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
