import { Injectable } from '@angular/core';
import { Need } from './need';

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
              private auth: AuthService) {}

  options: () => {headers: HttpHeaders} = this.auth.genOptions;

  getNeeds(): Observable<Need[]> {
    console.log('GET needs')
    return this.http.get<Need[]>(this.cupboardUrl, this.options())
     .pipe(catchError((err : HttpErrorResponse) => this.auth.catchHttpError<Need[]>(err)));
  }

  createNeed(need: Need): Observable<Need> {
    console.log('POST ' + need.name);
    return this.http.post<Need>(this.cupboardUrl, need, this.options())
     .pipe(catchError((err : HttpErrorResponse) => this.auth.catchHttpError<Need>(err)));
  }

  deleteNeed(name:string): Observable<Need> {
    console.log('DELETE ' + name);
    return this.http.delete<Need>(this.cupboardUrl + '/' + name, this.options())
     .pipe(catchError((err : HttpErrorResponse) => this.auth.catchHttpError<Need>(err)));
  }

  getNeed(name:string): Observable<Need> {
    console.log('GET ' + name);
    return this.http.get<Need>(this.cupboardUrl + '/' + name, this.options())
     .pipe(catchError((err : HttpErrorResponse) => this.auth.catchHttpError<Need>(err)));
  }

  searchNeeds(name:string): Observable<Need[]> {
    console.log('GET ?' + name);
    return this.http.get<Need[]>(this.cupboardUrl + "/?name=" + name, this.options())
     .pipe(catchError((err : HttpErrorResponse) => this.auth.catchHttpError<Need[]>(err)));
  }

  updateNeed(need: Need): Observable<Need> {
    console.log('PUT ' + need.name);
    return this.http.put<Need>(this.cupboardUrl, need, this.options())
     .pipe(catchError((err : HttpErrorResponse) => this.auth.catchHttpError<Need>(err)));
  }
  
}
