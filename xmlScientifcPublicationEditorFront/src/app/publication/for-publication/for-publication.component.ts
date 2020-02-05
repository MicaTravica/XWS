import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProcessPSPService } from 'src/app/services/processPSP/process-psp.service';
import { PublicationService } from 'src/app/services/publication-service/publication.service';
import { OpenServiceService } from 'src/app/services/open-service/open-service.service';
import { CoverLetterService } from 'src/app/services/cover-letter-service/cover-letter.service';

declare var require: any;
const convert = require('xml-js');


@Component({
  selector: 'app-for-publication',
  templateUrl: './for-publication.component.html',
  styleUrls: ['./for-publication.component.scss']
})
export class ForPublicationComponent implements OnInit {

  publications = [];

  constructor(
    private router: Router,
    private processPSPService: ProcessPSPService,
    private publicationService: PublicationService,
    private openService: OpenServiceService,
    private clService: CoverLetterService
  ) { }

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
          const p: any = processPSPList ;
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

  reviewers(id: string) {
    this.router.navigate(['/add_rev/' + id]);
  }

  evaluate(id: string) {
    this.router.navigate(['/process/' + id]);
  }

  xml(id: string) {
    this.publicationService.xml(id).subscribe(
      (data: any) => {
        this.openService.xml(data);
      }
    );
  }


  html(id: string) {
    this.publicationService.html(id).subscribe(
      (data: any) => {
        this.openService.html(data);
      });
  }

  pdf(id: string) {
    this.publicationService.pdf(id).subscribe(
      (data: any) => {
        this.openService.pdf(data);
      });
  }

   xmlCL(id: string) {
    this.clService.xml(id).subscribe(
      (data: any) => {
        this.openService.xml(data);
      }
    );
  }

  htmlCL(id: string) {
    this.clService.html(id).subscribe(
      (data: any) => {
        this.openService.html(data);
      });
  }

  pdfCL(id: string) {
    this.clService.pdf(id).subscribe(
      (data: any) => {
        this.openService.pdf(data);
      });
  }
}
