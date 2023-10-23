import { Injectable } from '@angular/core';
import { Need } from './need';

import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class CupboardService {
  private cupboardUrl = 'http://localhost:8080/inventory';
  
  constructor(private http: HttpClient) {}

  getCupboard(): Observable<Need[]> {
    return this.http.get<Need[]>(this.cupboardUrl);
  }
}
