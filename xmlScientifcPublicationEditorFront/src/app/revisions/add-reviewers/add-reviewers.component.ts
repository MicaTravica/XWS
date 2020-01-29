import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from 'src/app/services/user-service/user.service';
import { Person } from 'src/app/models/user-model/user.model';

declare var require: any;
const convert = require('xml-js');

@Component({
  selector: 'app-add-reviewers',
  templateUrl: './add-reviewers.component.html',
  styleUrls: ['./add-reviewers.component.scss']
})
export class AddReviewersComponent implements OnInit {

  reviewers = [];
  recommendedReviewers = [];
  selected = 0;
  chosenReviewers = [];

  constructor(
    private route: ActivatedRoute,
    private userService: UserService
  ) { }

  ngOnInit() {
    // tslint:disable-next-line: no-string-literal
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.userService.getReviewers().subscribe(
        (data: any) => {
          const obj = JSON.parse(convert.xml2json(data, {compact: true, spaces: 4}));
          this.reviewers = obj['ns:reviewers']['ns:person'] as Person[];
        }
      );
    }
    // ako radu nisu dodeljeni recezenti, tek je stavljen pod recenziju ucitati recenzente i preporuke
  }

  addReviwer() {
    this.chosenReviewers.push(this.reviewers[this.selected]);
    this.reviewers.splice(this.selected, 1);
    this.selected = 0;
  }

  finish() {
    // dodati recenzente i
    // promeniti stanje publikacije u to da se ceka odgovor od recezenata
  }
}

