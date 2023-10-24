import { Component, OnInit } from '@angular/core';
import { CupboardService } from '../cupboard.service';
import { Need } from '../need';
import { FundingBasketService } from '../funding-basket.service';


@Component({
  selector: 'app-cupboard',
  templateUrl: './cupboard.component.html',
  styleUrls: [ '../../styles.css', './cupboard.component.css']
})
export class CupboardComponent implements OnInit {
  constructor(private cupboardService: CupboardService, private fundingBasketService: FundingBasketService) {}

  cupboard: Need[] = []; 
  selectedNeed?: Need;
  admin = this.cupboardService.admin;
  
  ngOnInit(): void {
    this.getNeeds();
  }

  getNeeds(): void {
    this.cupboardService.getNeeds()
        .subscribe(cupboard => this.cupboard = cupboard);
  }

  onSelect(need: Need): void {
    this.selectedNeed = need;
  }

  addToBasket(): void {
    this.fundingBasketService.createNeed(this.selectedNeed!);
  }
}

