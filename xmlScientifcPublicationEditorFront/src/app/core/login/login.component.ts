import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { AuthService } from 'src/app/services/auth-service/auth.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/services/user-service/user.service';
import { NgxXml2jsonService } from 'ngx-xml2json';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm: FormGroup;

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private formBuilder: FormBuilder,
    private router: Router,
    private ngxXml2jsonService: NgxXml2jsonService
    // private toastr: ToastrService
  ) {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit() {
  }

  onSubmit() {
    this.userService.getPersonTemplate().subscribe(
      (template: any) => {
        const parser = new DOMParser();
        const xml = parser.parseFromString(template, 'text/xml');
        const obj = this.ngxXml2jsonService.xmlToJson(xml);
        console.log(obj);
      });
    // this.authService.login({ username: this.loginForm.value.username, password: this.loginForm.value.password }).subscribe(
    //   result => {
    //     console.log(result);
    //     // this.toastr.success('Successful login!');
    //     localStorage.setItem('token', JSON.stringify(result));
    //     this.router.navigate(['/homepage']);
    //   },
    //   error => {
    //     // this.toastr.error(error.error);
    //   }
    // );
  }

}
