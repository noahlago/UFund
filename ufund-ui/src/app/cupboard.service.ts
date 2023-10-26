import { Injectable } from '@angular/core';
import { Need } from './need';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';


@Injectable({
  providedIn: 'root'
})
export class CupboardService {
  private cupboardUrl = 'http://localhost:8080/inventory';
  
  constructor(private http: HttpClient, private auth: AuthService) {}

  options = this.auth.genHeaders();

  getNeeds(): Observable<Need[]> {
    console.log('GET needs')
    return this.http.get<Need[]>(this.cupboardUrl, this.options);
  }

  createNeed(need: Need): Observable<Need> {
    console.log('POST ' + need.name);
    return this.http.post<Need>(this.cupboardUrl, need, this.options);
  }

  deleteNeed(name:string) {
    console.log('DELETE ' + name);
    return this.http.delete<Need>(this.cupboardUrl + '/' + name, this.options);
  }

  getNeed(name:string): Observable<Need> {
    console.log('GET ' + name);
    return this.http.get<Need>(this.cupboardUrl + '/' + name, this.options);
  }

  searchNeeds(name:string): Observable<Need[]> {
    console.log('GET ?' + name);
    return this.http.get<Need[]>(this.cupboardUrl + "/?name=" + name, this.options);
  }

  updateNeed(need: Need): Observable<Need> {
    console.log('PUT ' + need.name);
    return this.http.put<Need>(this.cupboardUrl, need, this.options);
  }
  
}
