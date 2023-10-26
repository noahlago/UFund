import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LoginInfo } from './logininfo';

const authUrl = 'http://localhost:8080/auth/';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  loginInfo?: LoginInfo;
  
  constructor(private http: HttpClient) { }

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

  adminCheck(): boolean {
    return localStorage.getItem('username') === 'admin';
  }
  
  login(username:string, password:string) { 
    console.log('POST login: ' + username + ', ' + password);
    this.http.post<LoginInfo>(authUrl + 'login', {username, password}, this.genHeaders())
        .subscribe(info => {
                    console.log(info.token);
                    localStorage.setItem('username', info.username);
                    localStorage.setItem('token', info.token);
                  });
  }

  logout() {
    console.log('POST logout')
    this.http.post(authUrl + 'logout', {}, this.genHeaders());
  }

  register(username:string, password:string) {
    console.log('POST register: ' + username + ', ' + password);
    return this.http.post(authUrl + 'register', {username, password}, this.genHeaders());
  }
}
