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

  getCupboard(): Observable<Map<string, Need>> {
    return this.http.get<Map<string, Need>>(this.cupboardUrl);
  }
}
