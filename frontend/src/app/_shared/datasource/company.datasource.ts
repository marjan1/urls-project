import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {Observable} from "rxjs/Observable";

import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {catchError, finalize} from "rxjs/operators";
import {of} from "rxjs/observable/of";
import {AdvocateCompany} from "../../_model/advocate-company.model";
import {CompanyService} from "../../_service/company.service";


export class CompanyDatasource implements DataSource<AdvocateCompany> {

  private companiesSubject = new BehaviorSubject<AdvocateCompany[]>([]);

  private loadingSubject = new BehaviorSubject<boolean>(false);
  public loading$ = this.loadingSubject.asObservable();
  private numberOfCompaniesSubject = new BehaviorSubject<number>(0);
  public numberOfCompanies = this.numberOfCompaniesSubject.asObservable();

  constructor(private companyService: CompanyService) {

  }

  loadAdvocateCompanies(filter: string,
                        sortDirection: string,
                        pageIndex: number,
                        pageSize: number) {

    this.loadingSubject.next(true);

    this.companyService.getCompanyPage(filter, sortDirection,
      pageIndex, pageSize).pipe(
      catchError(() => of([])),
      finalize(() => this.loadingSubject.next(false))
    )
      .subscribe(pageOfCompanies => {
        this.companiesSubject.next(pageOfCompanies['content']);
        this.numberOfCompaniesSubject.next(pageOfCompanies['totalElements']);
      });

  }

  connect(collectionViewer: CollectionViewer): Observable<AdvocateCompany[]> {
    console.log("Connecting data source");
    return this.companiesSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.companiesSubject.complete();
    this.loadingSubject.complete();
  }

}


