import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user-service/user.service';
import { Validators, FormBuilder } from '@angular/forms';
import { Person } from 'src/app/models/user-model/user.model';
import { ToastrService } from 'ngx-toastr';

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
  id: string;

  constructor(
    private userService: UserService,
    private formBuilder: FormBuilder,
    private toastr: ToastrService,
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
      expertise: ['']
    });
  }

  get form() {
    return this.profilForm.controls;
  }

  ngOnInit() {
    this.userService.me().subscribe(
      (data: any) => {
        const obj = JSON.parse(convert.xml2json(data, { compact: true, spaces: 4 }));
        const person: Person = obj['ns:person'] as Person;
        this.id = person._attributes.id;
        this.profilForm = this.formBuilder.group({
          name: [person['ns:name']['_text'], Validators.required],
          surname: [person['ns:surname']['_text'], Validators.required],
          email: [person['ns:email']['_text'], Validators.required],
          phone: [person['ns:phone']['_text'], Validators.required],
          iName: [person['ns:institution']['ns:name']['_text'], Validators.required],
          city: [person['ns:institution']['ns:address']['ns:city']['_text'], Validators.required],
          streetNumber: [person['ns:institution']['ns:address']['ns:streetNumber']['_text']],
          floorNumber: [(person['ns:institution']['ns:address']['ns:floorNumber']) ? person['ns:institution']['ns:address']['ns:floorNumber']['_text'] : 0],
          street: [person['ns:institution']['ns:address']['ns:street']['_text'], Validators.required],
          country: [person['ns:institution']['ns:address']['ns:country']['_text'], Validators.required],
          expertise: [person['ns:expertise'] ? person['ns:expertise']['_text']: '']
        });
      }
    );
  }

  onSubmit() {
    this.submitted = true;
    if (this.profilForm.invalid) {
      return;
    }
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
        person._attributes.id = this.id;

        person['ns:expertise']["_text"] = this.profilForm.value.expertise;

        obj['ns:person'] = person;
        const retVal = convert.js2xml(obj, { compact: true, spaces: 4 });
        this.userService.savePerson(retVal).subscribe(
          (data: any) => {
            localStorage.setItem('user', data);
            this.toastr.success('You change your data!');
          },
          () => {
            this.toastr.error('Something went wrong, check your data!');
          }
        );
      });
  }

}
