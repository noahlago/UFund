import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router'
import { CupboardComponent } from './cupboard/cupboard.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  { path: 'cupboard', component: CupboardComponent },
  { path: 'login', component: LoginComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
