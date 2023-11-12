import { Injectable } from '@angular/core';
import { Need } from './need';
import { StatusService } from './status.service';

import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';
import { catchError } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class CupboardService {
  private cupboardUrl = 'http://localhost:8080/inventory';
  
  constructor(private http: HttpClient, 
              private auth: AuthService,
              private status: StatusService) {}

  options: {headers: HttpHeaders} = this.auth.genOptions();

  getNeeds(): Observable<Need[]> {
    console.log('GET needs')
    return this.http.get<Need[]>(this.cupboardUrl, this.options)
     .pipe(catchError((err : HttpErrorResponse) => {
        switch (err.status) {
          case (403):
            this.status.reportError('This service is not accessible without proper authentication.', '403; Forbidden');
            break;
          case (500):
            this.status.reportError('An internal error has occured, try again later.', '500');
            break;
          default:
            this.status.reportError('An unknown error occured', err.status.toString());
            break;
        }
        return new Observable<Need[]>;
      }));
  }

  createNeed(need: Need): Observable<Need> {
    console.log('POST ' + need.name);
    return this.http.post<Need>(this.cupboardUrl, need, this.options)
     .pipe(catchError((err : HttpErrorResponse) => {
        switch (err.status) {
          case (403):
            this.status.reportError('This service is not accessible without proper authentication.', '403; Forbidden');
            break;
          case (500):
            this.status.reportError('An internal error has occured, try again later.', '500');
            break;
          default:
            this.status.reportError('An unknown error occured', err.status.toString());
            break;
        }
        return new Observable<Need>;
      }));
  }

  deleteNeed(name:string) {
    console.log('DELETE ' + name);
    return this.http.delete<Need>(this.cupboardUrl + '/' + name, this.options)
     .pipe(catchError((err : HttpErrorResponse) => {
        switch (err.status) {
          case (403):
            this.status.reportError('This service is not accessible without proper authentication.', '403; Forbidden');
            break;
          case (500):
            this.status.reportError('An internal error has occured, try again later.', '500');
            break;
          default:
            this.status.reportError('An unknown error occured', err.status.toString());
            break;
        }
        return new Observable<Need>;
      }));
  }

  getNeed(name:string): Observable<Need> {
    console.log('GET ' + name);
    return this.http.get<Need>(this.cupboardUrl + '/' + name, this.options)
     .pipe(catchError((err : HttpErrorResponse) => {
        switch (err.status) {
          case (403):
            this.status.reportError('This service is not accessible without proper authentication.', '403; Forbidden');
            break;
          case (500):
            this.status.reportError('An internal error has occured, try again later.', '500');
            break;
          default:
            this.status.reportError('An unknown error occured', err.status.toString());
            break;
        }
        return new Observable<Need>;
      }));
  }

  searchNeeds(name:string): Observable<Need[]> {
    console.log('GET ?' + name);
    return this.http.get<Need[]>(this.cupboardUrl + "/?name=" + name, this.options)
     .pipe(catchError((err : HttpErrorResponse) => {
        switch (err.status) {
          case (403):
            this.status.reportError('This service is not accessible without proper authentication.', '403; Forbidden');
            break;
          case (500):
            this.status.reportError('An internal error has occured, try again later.', '500');
            break;
          default:
            this.status.reportError('An unknown error occured', err.status.toString());
            break;
        }
        return new Observable<Need[]>;
      }));
  }

  updateNeed(need: Need): Observable<Need> {
    console.log('PUT ' + need.name);
    return this.http.put<Need>(this.cupboardUrl, need, this.options)
     .pipe(catchError((err : HttpErrorResponse) => {
        switch (err.status) {
          case (403):
            this.status.reportError('This service is not accessible without proper authentication.', '403; Forbidden');
            break;
          case (500):
            this.status.reportError('An internal error has occured, try again later.', '500');
            break;
          default:
            this.status.reportError('An unknown error occured', err.status.toString());
            break;
        }
        return new Observable<Need>;
      }));
  }
  
}
