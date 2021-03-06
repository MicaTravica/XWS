import { Component, OnInit } from '@angular/core';
import { ProcessPSPService } from 'src/app/services/processPSP/process-psp.service';
import { PublicationService } from 'src/app/services/publication-service/publication.service';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http/http';
import { ToastrService } from 'ngx-toastr';
import { OpenServiceService } from 'src/app/services/open-service/open-service.service';
import { CoverLetterService } from 'src/app/services/cover-letter-service/cover-letter.service';

declare var require: any;
const convert = require('xml-js');


@Component({
  selector: 'app-my-publications',
  templateUrl: './my-publications.component.html',
  styleUrls: ['./my-publications.component.scss']
})
export class MyPublicationsComponent implements OnInit {

  publications = [];

  constructor(
    private processPSPService: ProcessPSPService,
    private router: Router,
    private toastr: ToastrService,
    private publicationService: PublicationService,
    private openService: OpenServiceService,
    private clService: CoverLetterService
  ) { }

  ngOnInit() {
    this.processPSPService.getMyPublications()
      .subscribe(res => {
          this.populateList(res);
    });
  }

  populateList(res: any) {
    this.publications = [];
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
  }

  addCoverLetter(processId: string) {
    this.router.navigate(['add_cover_letter', processId]);
  }

  retract(processId: string) {
    this.processPSPService.retractScientificPublication(processId).
      subscribe( res => {
        this.toastr.success(res);
    },
    (err: HttpErrorResponse)=>{
        this.toastr.error(err.error)
      },
      ()=>this.processPSPService.getMyPublications()
        .subscribe(res => {
            this.populateList(res)     
      })
    );
  }

  delete(processId: string) {
    this.processPSPService.deleteScientificPublication(processId).
      subscribe( res => {
        this.toastr.success(res);
    },
      (err: HttpErrorResponse) => {
        this.toastr.error(err.error);
      },
      () => this.processPSPService.getMyPublications()
        .subscribe(res => {
            this.populateList(res);
      })
    );
  }

  seeHistory(processId: string) {
    this.router.navigate(['see_revision', processId]);
  }

  getMetadataXML(scId: string) {
    this.publicationService.getMetadataXML(scId).subscribe( res => {
      this.openService.xml(res);
    });
  }

  getMetadataJSON(scId: string) {
    this.publicationService.getMetadataJSON(scId).subscribe( res => {
      this.openService.json(res);
    });
  }


  addNewVersion(processId: string) {
    this.router.navigate(['new_version', processId]);
  }

  xml(id: string) {
    this.publicationService.xmlVersion(id).subscribe(
      (data: any) => {
        this.openService.xml(data);
      }
    );
  }


  html(id: string) {
    this.publicationService.htmlVersion(id).subscribe(
      (data: any) => {
        this.openService.html(data);
      });
  }

  pdf(id: string) {
    this.publicationService.pdfVersion(id).subscribe(
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
