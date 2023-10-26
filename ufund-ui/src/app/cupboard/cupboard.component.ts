import { Component, OnInit } from '@angular/core';
import { CupboardService } from '../cupboard.service';
import { Need } from '../need';
import { FundingBasketService } from '../funding-basket.service';
import { FormBuilder, Validators } from '@angular/forms';


@Component({
  selector: 'app-cupboard',
  templateUrl: './cupboard.component.html',
  styleUrls: [ '../../styles.css', './cupboard.component.css']
})
export class CupboardComponent implements OnInit {
  constructor(private cupboardService: CupboardService, 
              private fundingBasketService: FundingBasketService,
              private formBuilder: FormBuilder) {}


  cupboard: Need[] = []; 
  selectedNeed?: Need;
  admin = this.cupboardService.admin;

  createForm = this.formBuilder.group({
    name: ['', Validators.required],
    cost: [0, Validators.required],
    quantity: [1, Validators.min(1)],
    type: ['', Validators.required],
  })
  
  ngOnInit(): void {
    this.getNeeds();
  }

  getNeeds(): void {
    this.cupboardService.getNeeds()
        .subscribe(cupboard => {
          this.cupboard = [];
          this.cupboard = cupboard
        });
  }

  onSelect(need: Need): void {
    this.selectedNeed = need;
  }

  onDelete(need: Need): void {
    this.cupboardService.deleteNeed(need.name)
      .subscribe(info => {
        console.log(info)
        this.getNeeds()
      });
  }

  onUpdate(need: Need): void {
    this.cupboardService.updateNeed(need)
      .subscribe(info => {
        console.log(info);
        this.getNeeds();
      });
    this.getNeeds()
  }

  onCreate() {
    let need: Need = {
      name: this.createForm.value.name!,
      cost: this.createForm.value.cost!,
      type: this.createForm.value.type!,
      quantity: this.createForm.value.quantity!
    }
    this.cupboardService.createNeed(need)
      .subscribe(info => { 
        console.log(info);
        this.getNeeds();
      });
  }

  addToBasket(): void {
    this.fundingBasketService.createNeed(this.selectedNeed!);
  }

}

