import { Component, OnInit } from '@angular/core';
import { FundingBasketService } from '../funding-basket.service';
import { Need } from '../need';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-funding-basket',
  templateUrl: './funding-basket.component.html',
  styleUrls: ['../../styles.css', './funding-basket.component.css']
})
export class FundingBasketComponent implements OnInit {

  constructor(private basketService: FundingBasketService,
              private formBuilder: FormBuilder) {}

  basket: Need[] = [];
  selectedNeed?: Need;
  
  checkoutForm = this.formBuilder.group({
    // Address info? Payment info?
  })

  ngOnInit(): void {
    this.getNeeds();
  }

  getNeeds(): void {
    this.basketService.getNeeds()
      .subscribe(needs => {
        this.basket = needs  
      })
  }

  deleteNeed(): void {
    this.basketService.deleteNeed(this.selectedNeed!.name).subscribe(() => this.getNeeds());
  }

  onSubmit(): void {
    
  }

  onSelect(need: Need): void {
    this.selectedNeed = need;
  }
  
}
