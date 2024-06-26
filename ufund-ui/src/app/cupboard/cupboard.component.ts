import { Component, OnInit } from '@angular/core';
import { CupboardService } from '../cupboard.service';
import { Need } from '../need';
import { FundingBasketService } from '../funding-basket.service';
import { FormBuilder, Validators } from '@angular/forms';
import { adminCheck } from '../auth.service';
import { StatusService } from '../status.service';


@Component({
  selector: 'app-cupboard',
  templateUrl: './cupboard.component.html',
  styleUrls: [ '../../styles.css', './cupboard.component.css']
})
export class CupboardComponent implements OnInit {
  constructor(private cupboardService: CupboardService,
              private fundingBasketService: FundingBasketService,
              private formBuilder: FormBuilder,
              private statusService: StatusService) {}


  cupboard: Need[] = []; 
  selectedNeed?: Need;
  admin = adminCheck;
  donationMatching?: boolean;

  createForm = this.formBuilder.group({
    name: ['', Validators.required],
    cost: [0, Validators.required],
    quantity: [1, Validators.min(1)],
    type: ['', Validators.required],
  })
  
  ngOnInit(): void {
    this.getNeeds();
    if (this.admin()) {
      this.getMatch();
    }
  }

  searchNeeds(term: string): void {
    if (!term.trim()) {
      this.getNeeds()
    } else {
      this.cupboardService.searchNeeds(term)
        .subscribe(needs => {
          this.cupboard = []
          this.cupboard = needs
        }); 
    }
  }

  getNeeds(): void {
    this.cupboardService.getNeeds()
        .subscribe(cupboard => {
          this.cupboard = [];
          this.cupboard = cupboard
        });
  }

  getMatch(): void {
    this.fundingBasketService.getMatch()
        .subscribe((match: boolean) => this.donationMatching = match);
  }

  onSelect(need: Need): void {
    this.selectedNeed = need;
  }

  onDelete(need: Need): void {
    this.cupboardService.deleteNeed(need.name)
      .subscribe(info => {
        this.statusService.reportGood(need.name + ' successfully deleted.', '200;\n' + info);
        this.getNeeds()
      });
  }

  onUpdate(need: Need): void {
    this.cupboardService.updateNeed(need)
      .subscribe(info => {
        this.statusService.reportGood(need.name + ' successfully rewritten.', '200;\n' + info);
        this.getNeeds();
      });
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
        this.statusService.reportGood(need.name + ' successfully created.', '201;\n' + info);
        this.getNeeds();
      });
  }

  addToBasket(): void {
    this.fundingBasketService.createNeed(this.selectedNeed!).subscribe();
  }

  matchToggle(): void {
    this.fundingBasketService.matchToggle().subscribe(() => {
      this.statusService.reportGood('Toggled donation matching.', '200; OK')
      this.getMatch();
    });
  }

}

