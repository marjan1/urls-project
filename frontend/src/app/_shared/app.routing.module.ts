import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from '../login/login.component';
import {PortalAdminComponent} from "../portal-admin/portal-admin.component";
import {CompanyAdminComponent} from "../company-admin/company-admin.component";
import {AdvocateComponent} from "../advocate/advocate.component";
import {ApprenticeComponent} from "../apprentice/apprentice.component";
import {ErrorPageComponent} from "../error-page/error-page.component";
import {PageNotFoundComponent} from "../page-not-found/page-not-found.component";
import {AuthGuard} from "../_service/auth-guard.service";
import {SignupComponent} from "../auth/signup/signup.component";

const routes: Routes = [
  {path: '', redirectTo: '/', pathMatch: 'full'},
  {path: 'portal-admin', canActivate: [AuthGuard], component: PortalAdminComponent,},
  {path: 'company-admin', component: CompanyAdminComponent},
  {path: 'advocate', component: AdvocateComponent},
  {path: 'apprentice', component: ApprenticeComponent},
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'error', component: ErrorPageComponent},
  {path: 'page-not-found', component: PageNotFoundComponent},
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
