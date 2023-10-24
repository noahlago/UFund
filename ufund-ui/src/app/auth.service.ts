import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

const authUrl = 'http://localhost:8080/login/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  token = "";
  username = "";
  
  constructor(private http: HttpClient) { }

  modHeaders() {
    httpOptions.headers.append('token', this.token);
    httpOptions.headers.append('username', this.username);
  }

  adminCheck(): boolean {
    return httpOptions.headers.get('username') === 'admin';
  }
  
  login(username:string, password:string) { 
    console.log('POST login: ' + username + ', ' + password);
    this.http.post<string>(authUrl, {username, password}, httpOptions)
        .subscribe(token => this.token = token);
    
    console.log(this.token);
    //httpOptions.headers.append('jwt', this.token);
  }

  logout() {
    console.log('POST logout')
    this.http.post(authUrl + 'logout', {}, httpOptions);
  }

  register(username:string, password:string) {
    console.log('POST register: ' + username + ', ' + password);
    return this.http.post(authUrl + 'register', {username, password}, httpOptions);
  }

  getOptions() {
    return httpOptions;
  } 
}
