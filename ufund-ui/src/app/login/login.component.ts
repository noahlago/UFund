import { Component, Input } from '@angular/core';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: [ './login.component.css', '../../styles.css' ]
})
export class LoginComponent {
  
  constructor(private authService: AuthService) {}

  @Input() username?: string;
  @Input() password?: string;

  login() {
    return this.authService.login(this.username!, this.password!);
  }

  logout() {
    return this.authService.logout();
  }

  register() {
    return this.authService.register(this.username!, this.password!);
  }
  
  
}
