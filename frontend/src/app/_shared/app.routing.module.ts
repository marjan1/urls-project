import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from '../login/login.component';
import {PortalAdminComponent} from "../portal-admin/portal-admin.component";
import {CompanyAdminComponent} from "../company-admin/company-admin.component";
import {AdvocateComponent} from "../advocate/advocate.component";
import {ApprenticeComponent} from "../apprentice/apprentice.component";

const routes: Routes = [
  { path: 'portal-admin', component: PortalAdminComponent },
  { path: 'company-admin', component: CompanyAdminComponent },
  { path: 'advocate', component: AdvocateComponent },
  { path: 'apprentice', component: ApprenticeComponent },
  { path: 'login', component: LoginComponent },
  {path : '', component : LoginComponent}
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
export class AppRoutingModule { }
