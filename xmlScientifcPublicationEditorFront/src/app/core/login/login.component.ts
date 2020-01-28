import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';

import { AuthService } from 'src/app/services/auth-service/auth.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/services/user-service/user.service';
import { Auth } from 'src/app/models/auth-model/auth.model';
import { Person } from 'src/app/models/user-model/user.model';

declare var require: any;
const convert = require('xml-js');


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
    private toastr: ToastrService
  ) {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      Password: ['', Validators.required]
    });
  }

  ngOnInit() {
  }

  onSubmit() {
    this.userService.getAuthTemplate().subscribe(
      (template: any) => {
        const obj = JSON.parse(convert.xml2json(template, { compact: true, spaces: 4 }));
        const auth: Auth = obj['ns:auth'] as Auth;
        auth['ns:password'] = this.loginForm.value.Password;
        auth['ns:email'] = this.loginForm.value.email;
        obj['ns:auth'] = auth;
        const retVal = convert.js2xml(obj, { compact: true, spaces: 4 });
        this.authService.login(retVal).subscribe(
          result => {
            localStorage.setItem('token', JSON.stringify(result));
            this.userService.me().subscribe(
              (data: any) => {
                localStorage.setItem('user', data);
                console.log(localStorage.getItem('user'));
              }
            );
            this.router.navigate(['/publications']);
          },
          error => {
            this.toastr.error(error.error);
          }
        );
      });
  }

}
