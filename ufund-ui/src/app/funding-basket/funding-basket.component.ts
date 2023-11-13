import { Component, OnInit } from '@angular/core';
import { FundingBasketService } from '../funding-basket.service';
import { Need } from '../need';

@Component({
  selector: 'app-funding-basket',
  templateUrl: './funding-basket.component.html',
  styleUrls: ['../../styles.css', './funding-basket.component.css']
})
export class FundingBasketComponent implements OnInit {

  constructor(private basketService: FundingBasketService) {}

  basket: Need[] = [];
  total: number = 0;
  selectedNeed?: Need;
  matching: string | null = null;
  
  ngOnInit(): void {
    this.getNeeds();
  }

  getNeeds(): void {
    this.basketService.getNeeds()
      .subscribe(needs => {
        this.basket = needs 
        this.setTotal()
        this.getMatch()
      })
  }

  getMatch(): void {
    this.basketService.getMatch().subscribe((match: boolean) => {
      this.matching = (match ? "We will match all purchases made." : null);
    })
  }

  setTotal(): void {
    this.total = 0
    for (let i = 0; i < this.basket.length; i++) {
      let need = this.basket[i];
      this.total += need.cost * need.quantity;
    }
  }

  deleteNeed(): void {
    this.basketService.deleteNeed(this.selectedNeed!.name).subscribe(() => this.getNeeds());
  }

  onSubmit(): void {
    this.basketService.checkout().subscribe(() => {
      this.basket = [];
      this.getNeeds()
    });
  }

  onSelect(need: Need): void {
    this.selectedNeed = need;
  }
  
}
