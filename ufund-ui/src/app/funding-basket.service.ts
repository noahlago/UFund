import { Injectable } from '@angular/core';
import { Need } from './need';

import { HttpClient, HttpHeaders, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs';
import { AuthService } from './auth.service';
import { StatusService } from './status.service';

@Injectable({
  providedIn: 'root'
})
export class FundingBasketService {
  private basketUrl = 'http://localhost:8080/basket';
  
  constructor(private http: HttpClient, 
              private auth: AuthService,
              private status: StatusService) {}

  options: {headers: HttpHeaders} = this.auth.genOptions();
  
  getNeeds(): Observable<Need[]> {
    console.log('GET needs')
    return this.http.get<Need[]>(this.basketUrl, this.options)
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
    return this.http.post<Need>(this.basketUrl, need, this.options);
  }

  deleteNeed(name:string): Observable<Need> {
    console.log('DELETE ' + name);
    return this.http.delete<Need>(this.basketUrl + '/' + name, this.options);
  }

  checkout(): Observable<undefined> {
    return this.http.post<undefined>(this.basketUrl + '/checkout', {} ,this.options);
  }
  
}
