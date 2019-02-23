import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {ErrorDialogComponent} from "./_shared/error-dialog.component";
import {LoginComponent} from "./login/login.component";
import {AuthService} from "./_service/auth.service";
import {TokenStorage} from "./_shared/token.storage";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {Interceptor} from "./_shared/inteceptor";
import {UserService} from "./_service/user.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CustomMaterialModule} from "./_shared/material.module";
import {AppRoutingModule} from "./_shared/app.routing.module";
import {PortalAdminComponent} from './portal-admin/portal-admin.component';
import {CompanyAdminComponent} from './company-admin/company-admin.component';
import {AdvocateComponent} from './advocate/advocate.component';
import {ApprenticeComponent} from './apprentice/apprentice.component';
import {HeaderComponent} from './header/header.component';
import {AppService} from "./_service/app.service";
import {ErrorPageComponent} from "./error-page/error-page.component";
import {PageNotFoundComponent} from "./page-not-found/page-not-found.component";
import {AuthGuard} from "./_service/auth-guard.service";
import {SignupComponent} from "./auth/signup/signup.component";
import {NewCompanyComponent} from './portal-admin/new-company/new-company.component';
import {CompaniesComponent} from './portal-admin/companies/companies.component';
import {AdminsComponent} from './portal-admin/admins/admins.component';
import {PaHeaderComponent} from './portal-admin/pa-header/pa-header.component';
import {CompanyService} from "./_service/company.service";

;


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ErrorDialogComponent,
    PortalAdminComponent,
    CompanyAdminComponent,
    AdvocateComponent,
    ApprenticeComponent,
    HeaderComponent,
    ErrorPageComponent,
    PageNotFoundComponent,
    SignupComponent,
    NewCompanyComponent,
    CompaniesComponent,
    AdminsComponent,
    PaHeaderComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    CustomMaterialModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [ErrorDialogComponent, UserService, AuthService, TokenStorage, TokenStorage,
    AuthGuard,
    AppService,
    CompanyService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: Interceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
