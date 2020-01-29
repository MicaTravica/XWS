import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProcessPSPService } from 'src/app/services/processPSP/process-psp.service';

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

  constructor(
    private route: ActivatedRoute,
    private processService: ProcessPSPService
  ) { }

  ngOnInit() {
    // tslint:disable-next-line: no-string-literal
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      
    }
    // ucitati rad
    // ako radu nisu dodeljeni recezenti, tek je stavljen pod recenziju ucitati recenzente i preporuke
    // ako su recenzenti odgovorili ucitati recenzije, prihvatiti odbiti rad...
  }
  accept() {

  }

  reject() {

  }

  revise() {

  }
  
}
