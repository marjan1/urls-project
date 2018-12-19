import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {UserComponent} from "./user/user.component";
import {ErrorDialogComponent} from "./core/error-dialog.component";
import {LoginComponent} from "./login/login.component";
import {AuthService} from "./service/auth.service";
import {TokenStorage} from "./core/token.storage";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {Interceptor} from "./core/inteceptor";
import {UserService} from "./service/user.service";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {FormsModule} from "@angular/forms";
import {CustomMaterialModule} from "./core/material.module";
import {AppRoutingModule} from "./core/app.routing.module";

@NgModule({
  declarations: [
    AppComponent,
    UserComponent,
    LoginComponent,
    ErrorDialogComponent],
  imports: [
    BrowserModule,
    HttpClientModule,
    BrowserAnimationsModule,
    CustomMaterialModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [ErrorDialogComponent, UserService, AuthService, TokenStorage, TokenStorage,
    {provide: HTTP_INTERCEPTORS,
      useClass: Interceptor,
      multi : true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
