import { Injectable } from '@angular/core';
import { Need } from './need';

import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class FundingBasketService {
  private basketUrl = 'http://localhost:8080/basket';
  
  constructor(private http: HttpClient, private auth: AuthService) {}

  options = this.auth.genHeaders();
  
  getNeeds(): Observable<Need[]> {
    console.log('GET needs')
    return this.http.get<Need[]>(this.basketUrl, this.options);
  }

  createNeed(need: Need): Observable<Need> {
    console.log('POST ' + need.name);
    return this.http.post<Need>(this.basketUrl, need, this.options);
  }

  deleteNeed(name:string) {
    console.log('DELETE ' + name);
    return this.http.delete<Need>(this.basketUrl + '/' + name, this.options);
  }

  checkout() {
    return this.http.post<Object>(this.basketUrl + '/checkout', {} ,this.options);
  }
  
}
