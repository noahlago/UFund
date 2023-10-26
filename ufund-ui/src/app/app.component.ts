import { Component } from '@angular/core';
import { AuthService, adminCheck, loginCheck, getUsername } from './auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  admin = adminCheck
  login = loginCheck
  username = getUsername
  

  constructor(private authService: AuthService ) {}

  logout(): void {
    this.authService.logout();
  }

  checkRoot() {
    return location.pathname === '/'
  }
}
