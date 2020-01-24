import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-process-publication',
  templateUrl: './process-publication.component.html',
  styleUrls: ['./process-publication.component.scss']
})
export class ProcessPublicationComponent implements OnInit {

  publication: any;
  reviewers = [{ name: 'ime', surname: 'prezime' }, { name: 'hehe', surname: 'cao' }];
  recommendedReviewers = [{ name: 'ime', surname: 'prezime' }];
  selected = 0;
  chosenReviewers = [];
  review = [{ author: 'neki autor' }];

  constructor() { }

  ngOnInit() {
    // ucitati rad
    // ako radu nisu dodeljeni recezenti, tek je stavljen pod recenziju ucitati recenzente i preporuke
    // ako se ceka na odgovor od recenzenata nista ne ucitavati
    // ako su recenzenti odgovorili ucitati recenzije, prihvatiti odbiti rad...
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

  accept() {

  }

  reject() {

  }

  revise() {

  }
}
