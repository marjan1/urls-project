import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {CompanyUser} from "../_model/company-user.model";
import {AdvocateCompany} from "../_model/advocate-company.model";


@Injectable()
export class CompanyService {

  private baseUrl = 'http://localhost:8080/api/advocatecompany';

  constructor(private http: HttpClient) {
  }


  addNewCompanyWithAdmin(companyUser: CompanyUser): Observable<AdvocateCompany> {
    console.log('signup new company with admin user  ', companyUser);
    return this.http.post<AdvocateCompany>(this.baseUrl + '/addwithadmin',
      companyUser);
  }

  editAdvocateCompany(advocateCompany: AdvocateCompany): Observable<AdvocateCompany> {
    console.log('edit company  ', advocateCompany);
    return this.http.post<AdvocateCompany>(this.baseUrl + '/add',
      advocateCompany);
  }

  getCompanyPage( filter = '', sortOrder = 'asc',
                  pageNumber = 0, pageSize = 3): Observable<AdvocateCompany[]>{
    console.log('get advocate companies page with number {} and size {}  ', pageNumber);
    let params = new HttpParams();

    // Begin assigning parameters
    params = params.append('pageNumber',pageNumber.toString());
    params = params.append('size', pageSize.toString());
    params = params.append('filter', filter);
    params = params.append('sortOrder', sortOrder);
    return this.http.get<AdvocateCompany[]>(this.baseUrl + '/page',{params : params});

  //    .pipe(map( rez => rez['content']));
  }

}
