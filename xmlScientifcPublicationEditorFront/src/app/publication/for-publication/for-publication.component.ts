import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProcessPSPService } from 'src/app/services/processPSP/process-psp.service';

declare var require: any;
const convert = require('xml-js');


@Component({
  selector: 'app-for-publication',
  templateUrl: './for-publication.component.html',
  styleUrls: ['./for-publication.component.scss']
})
export class ForPublicationComponent implements OnInit {

  publications = [];

  constructor(private router: Router,
              private processPSPService: ProcessPSPService) { }

  ngOnInit() {
    this.processPSPService.getPublicationsForPublishing()
      .subscribe(res => {
        const obj = JSON.parse(convert.xml2json(res, { compact: true, spaces: 4 }));
        const processPSPList = obj.processes.processPSP as any[];
        if (processPSPList.length) {
          processPSPList.forEach(p => {
            this.publications.push({
              id: p.sp.scientificPublicationId._text,
              name: p.sp.scientificPublicationName._text,
              authors: (p.sp.authors.author.length) ? p.sp.authors.author : [p.sp.authors.author],
              processState: p.processState._text,
              lastVersion: p.lastVersion._text,
              processId: p.processId._text
            });
          });
        } else {
          const p = processPSPList;
          this.publications.push({
            id: p.sp.scientificPublicationId._text,
            name: p.sp.scientificPublicationName._text,
            authors: (p.sp.authors.author.length) ? p.sp.authors.author : [p.sp.authors.author],
            processState: p.processState._text,
            lastVersion: p.lastVersion._text,
            processId: p.processId._text
          });
        }
      });
  }

  reviewers(id: number) {
    this.router.navigate(['/add_rev/' + id]);
  }

  evaluate(id: number) {
    this.router.navigate(['/process/' + id]);
  }
}
