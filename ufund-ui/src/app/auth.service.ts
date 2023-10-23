import { Injectable } from '@angular/core';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const authUrl = 'http://localhost:8080/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  // TODO: Storage and providing of JWT token. How?
  
  login(username:string, password:string) {
    return this.http.post(authUrl + 'login', {username, password}, httpOptions);
  }

  logout() {
    return this.http.post(authUrl + 'logout', {}, httpOptions);
  }

  register(username:string, password:string) {
    return this.http.post(authUrl + 'register', {username, password}, httpOptions);
  }
  
}
