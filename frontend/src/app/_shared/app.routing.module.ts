import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from '../login/login.component';
import {InitComponent} from "../portal-admin/init.component";
import {SignupComponent} from "../auth/signup/signup.component";
import {NewLinkComponent} from "../portal-admin/new-link/new-link.component";

const routes: Routes = [
  {path: '', redirectTo: '/', pathMatch: 'full'},
  {
    path: 'init',

    component: InitComponent,
    children: [
      {path: '', component: NewLinkComponent},
      {path: 'new', component: NewLinkComponent}
    ]
  },

  {path: 'login', component: LoginComponent},
  {path: 'signup', component: SignupComponent},
  {path: '', component: LoginComponent}
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule {
}
