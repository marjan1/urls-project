import {CollectionViewer, DataSource} from "@angular/cdk/collections";
import {Observable} from "rxjs/Observable";

import {BehaviorSubject} from "rxjs/BehaviorSubject";
import {catchError, finalize} from "rxjs/operators";
import {of} from "rxjs/observable/of";
import {UserService} from "../../_service/user.service";
import {CUser} from "../../_model/cuser.model";


export class AdminDatasource implements DataSource<CUser> {

  private adminsSubject = new BehaviorSubject<CUser[]>([]);

  private loadingSubject = new BehaviorSubject<boolean>(false);
  public loading$ = this.loadingSubject.asObservable();
  private numberOfAdminsSubject = new BehaviorSubject<number>(0);
  public numberOfAdmins = this.numberOfAdminsSubject.asObservable();

  constructor(private userService: UserService) {

  }

  loadAdmins(filter: string,
             sortDirection: string,
             pageIndex: number,
             pageSize: number) {

    this.loadingSubject.next(true);

    this.userService.getAdminsPage(filter, sortDirection,
      pageIndex, pageSize).pipe(
      catchError(() => of([])),
      finalize(() => this.loadingSubject.next(false))
    )
      .subscribe(pageOfAdmins => {
        this.adminsSubject.next(pageOfAdmins['content']);
        this.numberOfAdminsSubject.next(pageOfAdmins['totalElements']);
      });

  }

  connect(collectionViewer: CollectionViewer): Observable<CUser[]> {
    console.log("Connecting data source");
    return this.adminsSubject.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.adminsSubject.complete();
    this.loadingSubject.complete();
  }

}


