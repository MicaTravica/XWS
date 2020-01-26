import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

import { UserService } from '../../services/user-service/user.service'
import { Auth } from 'src/app/models/auth-model/auth.model';

declare var require: any;
const convert = require('xml-js');


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm;
  submitted;
  
  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder
  ) {
    this.registerForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  ngOnInit() {
  }

  get form() {
    return this.registerForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    if (this.registerForm.invalid) {
      return;
    }
    this.userService.getAuthTemplate().subscribe(
      (template: any) => {
        const obj = JSON.parse(convert.xml2json(template, {compact: true, spaces: 4}));
        const auth: Auth = obj['ns:auth'] as Auth;
        auth['ns:password'] = this.registerForm.value.password;
        auth['ns:email'] = this.registerForm.value.email;
        obj['ns:auth'] = auth;
        const retVal = convert.js2xml(obj, {compact: true, spaces: 4});
        this.userService.save(retVal);
      });
  }
}
