import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../auth.service';
import { StatusService } from '../status.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: [ './login.component.css', '../../styles.css' ]
})
export class LoginComponent {

  username?: string;
  password?: string;
  buttonType?: string;
  loginForm: FormGroup = this.formBuilder.group({
      username: ['', [Validators.required, Validators.pattern('^[a-zA-Z0-9]+$')]],
      password: ['', Validators.required]
    });
    
  constructor(
    private authService: AuthService, 
    private formBuilder: FormBuilder,
    private statusService: StatusService) {}

  onSubmit(): void {
    if (!this.loginForm.valid) {  
      this.statusService.reportError('Make sure your username and password are valid.', 'Form Validation Failed');
      return;
    }
    console.log(this.loginForm.value.username)
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

  onLoginClick(): void {
    this.buttonType = "login";
  }

  onRegisterClick(): void {
    this.buttonType = "register";
  } 

  login(): void {
    this.authService.login(this.username!, this.password!)
      .subscribe(info => {
                    localStorage.setItem('username', info.username);
                    localStorage.setItem('token', info.token)
                    this.statusService.reportGood('Successfully Logged In', '200; Credentials Saved')
                  });
  }

  register(): void {
    this.authService.register(this.username!, this.password!)
        .subscribe((info) => {
                    localStorage.setItem('username', info.username);
                    localStorage.setItem('token', info.token);
                    this.statusService.reportGood('Successfully Created Account', '201; User Created')
                  });
  }  
}
