import {BrowserModule} from '@angular/platform-browser';
import {APP_INITIALIZER, NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {ErrorDialogComponent} from "./_shared/error-dialog.component";
import {LoginComponent} from "./login/login.component";
import {AuthService} from "./_service/auth.service";
import {TokenStorage} from "./_shared/token.storage";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {Interceptor} from "./_shared/inteceptor";
import {LinkService} from "./_service/link.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CustomMaterialModule} from "./_shared/material.module";
import {AppRoutingModule} from "./_shared/app.routing.module";
import {InitComponent} from './portal-admin/init.component';
import {HeaderComponent} from './header/header.component';
import {AppService} from "./_service/app.service";
import {SignupComponent} from "./auth/signup/signup.component";
import {NewLinkComponent} from './portal-admin/new-link/new-link.component';
import {CompaniesComponent} from './portal-admin/companies/companies.component';


export function appInitFactory(provider: AppService) {
  return () => provider.load();
}

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ErrorDialogComponent,
    InitComponent,
    HeaderComponent,
    SignupComponent,
    NewLinkComponent,
    CompaniesComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    CustomMaterialModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule
  ],
  providers: [ErrorDialogComponent, LinkService, AuthService, TokenStorage, TokenStorage,

    AppService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: Interceptor,
      multi: true
    },
    {provide: APP_INITIALIZER, useFactory: appInitFactory, deps: [AppService], multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
