import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {Status} from "../_model/status.model";

@Injectable()
export class AppService {

  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  public getStatuses(): Observable<Status[]> {
    return this.http.get<Status[]>(this.baseUrl + '/app/statuses');
  }

}
