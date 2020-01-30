import { Component, OnInit } from '@angular/core';
import { ProcessPSPService } from 'src/app/services/processPSP/process-psp.service';
import { Router } from '@angular/router';

declare var require: any;
const convert = require('xml-js');


@Component({
  selector: 'app-my-publications',
  templateUrl: './my-publications.component.html',
  styleUrls: ['./my-publications.component.scss']
})
export class MyPublicationsComponent implements OnInit {

  publications = [];

  constructor(private processPSPService: ProcessPSPService,
              private router: Router ) { }

  ngOnInit() {
    this.processPSPService.getMyPublications()
      .subscribe(res => {
        const obj = JSON.parse(convert.xml2json(res, { compact: true, spaces: 4 }));
        if (obj.processes.processPSP) {
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
            const p = processPSPList as any;
            this.publications.push({
              id: p.sp.scientificPublicationId._text,
              name: p.sp.scientificPublicationName._text,
              authors: (p.sp.authors.author.length) ? p.sp.authors.author : [p.sp.authors.author],
              processState: p.processState._text,
              lastVersion: p.lastVersion._text,
              processId: p.processId._text
            });
          }
        }
      });
  }

  addCoverLetter(processId: string) {
    this.router.navigate(['add_cover_letter', processId]);
  }

  retract(processId: string) {
    // dodati otkazivanje
  }

  delete(processId: string) {
    // dodati otkazivanje
  }

  seeHistory(processId: string) {
    this.router.navigate(['see_revision', processId]);
  }


}
