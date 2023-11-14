import { Component, OnInit } from '@angular/core';
import { StatusType } from '../status.type';
import { StatusService } from '../status.service';

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.css']
})
export class StatusComponent implements OnInit {

  constructor(private statusService: StatusService) {}
  
  type?: StatusType;
  message?: string | null;

  ngOnInit(): void {
    this.statusService.message.subscribe(value => this.message = value);
    this.statusService.status.subscribe(value => this.type = value);
  }
  
  clear(): void {
    this.statusService.clear()
  }

  isGood(): boolean {
    return this.type === StatusType.GOOD;
  }

  isError(): boolean {
    return this.type === StatusType.ERROR;
  }

  isInfo(): boolean {
    return this.type === StatusType.INFO;
  }
}
