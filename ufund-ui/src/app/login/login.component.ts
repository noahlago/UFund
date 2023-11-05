import { Component } from '@angular/core';
import { FormBuilder, Validator, Validators } from '@angular/forms';
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
    username: ['', Validators.required],
    password: ['', Validators.required]
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
        break;
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
    this.authService.login(this.username!, this.password!)
      .subscribe(info => {
                    localStorage.setItem('username', info.username);
                    localStorage.setItem('token', info.token)
                  });
  }

  logout() {
    this.authService.logout()
      .subscribe(() => {
        localStorage.clear()
      });
  }

  register() {
    this.authService.register(this.username!, this.password!)
        .subscribe(info => {
                    localStorage.setItem('username', info.username);
                    localStorage.setItem('token', info.token);
                  });
  }  
}
