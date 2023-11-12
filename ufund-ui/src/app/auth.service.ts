import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { LoginInfo } from './logininfo';
import { StatusService } from './status.service';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

const authUrl: string = 'http://localhost:8080/auth/';

export function adminCheck(): boolean {
  return localStorage.getItem('username') === 'admin';
}

export function loginCheck(): boolean {
  let username = localStorage.getItem('username');
  let token = localStorage.getItem('token');
  return username !== null && token !== null
}

export function getUsername(): string | null {
  return localStorage.getItem('username')
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loginInfo?: LoginInfo;
  
  constructor(private http: HttpClient,
              private status: StatusService ) { }

  genOptions(): {headers: HttpHeaders} {
    let username = localStorage.getItem('username');
    let token = localStorage.getItem('token');
    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'username': username === null ? '' : username,
        'token': token === null ? '' : token
      })
    }
  }

  
  login(username:string, password:string): Observable<LoginInfo> { 
    this.status.reportInfo(null, 'POST /auth/login');
    return this.http.post<LoginInfo>(authUrl + 'login', {username, password}, this.genOptions())
     .pipe(catchError((err : HttpErrorResponse) => {
        switch (err.status) {
          case (403):
            this.status.reportError('Either your username or password is incorrect.', '403; Invalid Credentials.');
            break;
          case (500):
            this.status.reportError('An internal error has occured, try again later.', '500');
            break;
          default:
            this.status.reportError('An unknown error occured', err.status.toString());
            break;
        }
        return new Observable<LoginInfo>;
      }));
  }

  logout(): Observable<undefined> {
    this.status.reportInfo(null, 'POST /auth/logout')
    return this.http.post<undefined>(authUrl + 'logout', {}, this.genOptions())
      .pipe(catchError((err: HttpErrorResponse) => {
        switch (err.status) {
          case (403):
            /*On API reset the keys are cleared but
            Frontend is not privy to this. This catches
            the edge case and clears storage to prevent
            a lockout.
            */
            this.status.reportGood('Logged Out', '403; Clearing bad credentials.');
            localStorage.clear();
            break;
          case (500):
            this.status.reportError('An internal error has occured, try again later.', '500');
            break;
          default:
            this.status.reportError('An unknown error occured', err.status.toString());
            break;
        }
        return new Observable<undefined>;
      }));
  }

  register(username:string, password:string): Observable<LoginInfo> {
    this.status.reportInfo(null, 'POST /auth/register');
    return this.http.post<LoginInfo>(authUrl + 'register', {username, password}, this.genOptions())
     .pipe(catchError((err : HttpErrorResponse) => {
        switch (err.status) {
          case (409):
            this.status.reportError('A user with that username exists.', '409; Username Conflict');
            break;
          case (500):
            this.status.reportError('An internal error has occured, try again later.', '500');
            break;
          default:
            this.status.reportError('An unknown error occured', err.status.toString());
            break;
        }
        return new Observable<LoginInfo>;
      }));
  }
}
