import { Component, OnInit } from '@angular/core';
import { CupboardService } from '../cupboard.service';
import { Need } from '../need';


@Component({
  selector: 'app-cupboard',
  templateUrl: './cupboard.component.html',
  styleUrls: ['./cupboard.component.css']
})
export class CupboardComponent implements OnInit {
  constructor(private cupboardService: CupboardService) {}

  cupboard: Need[] = []; 
  
  ngOnInit(): void {
    this.getCupboard();
  }

  getCupboard(): void {
    this.cupboardService.getCupboard()
        .subscribe(cupboard => this.cupboard = Array.from(cupboard.keys(), (key) => cupboard.get(key)) as Need[] );
  }
}

