import { Injectable } from '@angular/core';
import { StatusType } from './status.type';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StatusService {

  message: BehaviorSubject<string | null> = new BehaviorSubject<string | null>(null);
  status: BehaviorSubject<StatusType> = new BehaviorSubject<StatusType>(StatusType.GOOD);
  
  constructor() { }

  clear(): void {
    this.message.next(null)
    this.status.next(StatusType.GOOD);
  }
 
  private report(message: string | null, log: string, status: StatusType): void { 
    console.log(status.valueOf() + log);
    this.message.next(message);
    this.status.next(status);
  }

  reportError(message: string | null, log: string): void {
    this.report(message, log, StatusType.ERROR);
  }

  reportInfo(message: string | null, log: string): void {
    this.report(message, log, StatusType.INFO);
  }

  reportGood(message: string | null, log: string): void {
    this.report(message, log, StatusType.GOOD);
  }

}
