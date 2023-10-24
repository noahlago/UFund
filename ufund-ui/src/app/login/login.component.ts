import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: [ './login.component.css', '../../styles.css' ]
})
export class LoginComponent {

  username?: string;
  password?: string;
  buttonType?: string;
  loginForm = this.formBuilder.group({
    username: '',
    password: ''
  });
  
  constructor(
    private authService: AuthService, 
    private formBuilder: FormBuilder) {}



  onSubmit() {
    this.username = this.loginForm.value.username!;
    this.password = this.loginForm.value.password!;
    
    switch (this.buttonType) {
      case "login": {
        this.login();
        break;
      }
      case "register": {
        this.register();
      }
    }
  }

  onLoginClick() {
    this.buttonType = "login";
  }

  onRegisterClick() {
    this.buttonType = "register";
  } 

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
