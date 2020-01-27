import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user-service/user.service';
import { Validators, FormBuilder } from '@angular/forms';
import { Person } from 'src/app/models/user-model/user.model';

declare var require: any;
const convert = require('xml-js');

@Component({
  selector: 'app-profil-edit',
  templateUrl: './profil-edit.component.html',
  styleUrls: ['./profil-edit.component.scss']
})
export class ProfilEditComponent implements OnInit {

  profilForm;
  submitted;

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder
  ) {
    this.profilForm = this.formBuilder.group({
      name: ['', Validators.required],
      surname: ['', Validators.required],
      email: ['', Validators.required],
      phone: ['', Validators.required],
      iName: ['', Validators.required],
      city: ['', Validators.required],
      streetNumber: [''],
      floorNumber: [''],
      street: ['', Validators.required],
      country: ['', Validators.required],

    });
  }

  get form() {
    return this.profilForm.controls;
  }

  ngOnInit() {
  }

  onSubmit() {
    this.submitted = true;
    if (this.profilForm.invalid) {
      return;
    }
    console.log('cao');
    this.userService.getPersonTemplate().subscribe(
      (template: any) => {
        const obj = JSON.parse(convert.xml2json(template, { compact: true, spaces: 4 }));
        const person: Person = obj['ns:person'] as Person;
        person['ns:name'] = this.profilForm.value.name;
        person['ns:surname'] = this.profilForm.value.surname;
        person['ns:email'] = this.profilForm.value.email;
        person['ns:phone'] = this.profilForm.value.phone;
        person['ns:institution']['ns:name'] = this.profilForm.value.iName;
        person['ns:institution']['ns:address']['ns:city'] = this.profilForm.value.city;
        if (this.profilForm.value.streetNumber === '') {
          person['ns:institution']['ns:address']['ns:streetNumber'] = 0;
        } else {
          person['ns:institution']['ns:address']['ns:streetNumber'] = this.profilForm.value.streetNumber;
        }
        if (this.profilForm.value.floorNumber === '') {
          person['ns:institution']['ns:address']['ns:floorNumber'] = 0;
        } else {
          person['ns:institution']['ns:address']['ns:floorNumber'] = this.profilForm.value.floorNumber;
        }
        person['ns:institution']['ns:address']['ns:street'] = this.profilForm.value.street;
        person['ns:institution']['ns:address']['ns:country'] = this.profilForm.value.country;
        obj['ns:person'] = person;
        const retVal = convert.js2xml(obj, { compact: true, spaces: 4 });
        this.userService.savePerson(retVal);
      });
  }

}
