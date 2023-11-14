import { Component } from '@angular/core';
import { AuthService, adminCheck, loginCheck, getUsername } from './auth.service';
import { StatusService } from './status.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  admin = adminCheck
  login = loginCheck
  username = getUsername
  
  constructor(private authService: AuthService,
              private statusService: StatusService ) {}

  logout(): void {
    this.authService.logout().subscribe(() => {
      localStorage.clear()
      this.statusService.reportGood('Logged Out', '200; Credentials Cleared')
    });
  }

  checkRoot(): boolean {
    return location.pathname === '/'
  }

  clearStatus(): void {
    this.statusService.clear();
  }
}
