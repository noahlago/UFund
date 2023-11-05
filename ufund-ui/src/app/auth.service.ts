import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { LoginInfo } from './logininfo';
import { StatusService } from './status.service';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';

const authUrl = 'http://localhost:8080/auth/';

export function adminCheck(): boolean {
  return localStorage.getItem('username') === 'admin';
}

export function loginCheck(): boolean {
  let username = localStorage.getItem('username');
  let token = localStorage.getItem('token');
  return username !== null && token !== null
}

export function getUsername() {
  return localStorage.getItem('username')
}


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loginInfo?: LoginInfo;
  
  constructor(private http: HttpClient,
              private status: StatusService ) { }

  genHeaders() {
    let username = localStorage.getItem('username');
    let token = localStorage.getItem('token');
    console.log('username: ' + username + '\ntoken: ' + token);
    return {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'username': username === null ? '' : username,
        'token': token === null ? '' : token
      })
    }
  }

  
  login(username:string, password:string) { 
    console.log('POST login: ' + username + ', ' + password);
    return this.http.post<LoginInfo>(authUrl + 'login', {username, password}, this.genHeaders())
      .pipe(catchError((err: HttpErrorResponse) => {
        if (err.status == 403) {
          localStorage.clear()
        }
        return new Observable<LoginInfo>;
      }));
  }

  logout() {
    console.log('POST logout')
    return this.http.post(authUrl + 'logout', {}, this.genHeaders())
  }

  register(username:string, password:string) {
    console.log('POST register: ' + username + ', ' + password);
    return this.http.post<LoginInfo>(authUrl + 'register', {username, password}, this.genHeaders())
  }
}
