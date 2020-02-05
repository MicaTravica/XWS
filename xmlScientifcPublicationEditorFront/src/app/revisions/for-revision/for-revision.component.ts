import { Component, OnInit } from '@angular/core';
import { ProcessPSPService } from 'src/app/services/processPSP/process-psp.service';
import { Router } from '@angular/router';
import { PublicationService } from 'src/app/services/publication-service/publication.service';
import { OpenServiceService } from 'src/app/services/open-service/open-service.service';

declare var require: any;
const convert = require('xml-js');


@Component({
  selector: 'app-for-revision',
  templateUrl: './for-revision.component.html',
  styleUrls: ['./for-revision.component.scss']
})
export class ForRevisionComponent implements OnInit {

  publications = [];

  constructor(
    private processPSPService: ProcessPSPService,
    private router: Router,
    private publicationService: PublicationService,
    private openService: OpenServiceService
  ) { }


  getMyReviewAssigment( res: any) {
    this.publications = [];
    const obj = JSON.parse(convert.xml2json(res, { compact: true, spaces: 4 }));
    if (obj.processes.processPSP) {
      const processPSPList = obj.processes.processPSP as any[];
      if (processPSPList.length) {
        processPSPList.forEach(p => {
        this.publications.push({
          id: p.sp.scientificPublicationId._text,
          name: p.sp.scientificPublicationName._text,
          processState: p.processState._text,
          lastVersion: p.lastVersion._text,
          processId: p.processId._text,
          reviewAssigmentState: p.reviewAssigmentState._text,
          reviewState: p.reviewState._text
          });
        });
      } else {
      const p = processPSPList as any;
      this.publications.push({
        id: p.sp.scientificPublicationId._text,
        name: p.sp.scientificPublicationName._text,
        processState: p.processState._text,
        lastVersion: p.lastVersion._text,
        processId: p.processId._text,
        reviewAssigmentState: p.reviewAssigmentState._text,
        reviewState: p.reviewState._text
        });
      }
    }
  }

  ngOnInit() {
    this.processPSPService.getMyReviewAssigments()
        .subscribe( res => {
          this.getMyReviewAssigment(res);
      });
  }

  accept(processData: any) {
    this.processPSPService.getReviewAssigmentTemplate().subscribe(
      templ => {
        const obj = JSON.parse(convert.xml2json(templ, { compact: true, spaces: 4 }));
        obj['ns:reviewAssigmentAcceptance']['ns:processId']._text = processData.processId;
        obj['ns:reviewAssigmentAcceptance']['ns:processVersion']._text = processData.lastVersion,
        obj['ns:reviewAssigmentAcceptance']['ns:decision']._text = 'accepted';
        const payLoadXML: any = convert.js2xml(obj, { compact: true, spaces: 4 });
        this.processPSPService.acceptRejectAssigmentReview(payLoadXML).subscribe(res => {
        },
          (err) => { console.log(err); },
        () => {
          this.processPSPService.getMyReviewAssigments()
              .subscribe( res => {
                this.getMyReviewAssigment(res);
            });
        });
    }, err => {
      console.log(err);
    });
  }

  reject(processData: any) {
    this.processPSPService.getReviewAssigmentTemplate().subscribe(
      templ => {
        const obj = JSON.parse(convert.xml2json(templ, { compact: true, spaces: 4 }));
        obj['ns:reviewAssigmentAcceptance']['ns:processId']._text = processData.processId;
        obj['ns:reviewAssigmentAcceptance']['ns:processVersion']._text = processData.lastVersion,
        obj['ns:reviewAssigmentAcceptance']['ns:decision']._text = 'rejected';
        const payLoadXML: any = convert.js2xml(obj, { compact: true, spaces: 4 });
        this.processPSPService.acceptRejectAssigmentReview(payLoadXML).subscribe(res => {
          console.log(res);
        },
        (err) => {
          console.log(err);
        },
        () => {
          this.processPSPService.getMyReviewAssigments()
              .subscribe( res => {
                this.getMyReviewAssigment(res);
            });
          }
        );
      },
      err => {
        console.log(err);
      });
  }

  addReview(sp: any) {
    this.router.navigate(['add_revision', sp.processId]);
  }

  addComments(sp: any) {
    this.router.navigate(['add_comments', sp.processId]);
  }

  xml(id: string) {
    this.publicationService.getPublicationReviewerProcess(id).subscribe(
      (data: any) => {
        this.openService.xml(data);
      }
    );
  }
  html(id: string) {
    this.publicationService.getPublicationReviewerProcessHTML(id).subscribe(
      (data: any) => {
        this.openService.html(data);
      });
  }

  pdf(id: string) {
    this.publicationService.getPublicationReviewerProcessPDF(id).subscribe(
      (data: any) => {
        this.openService.pdf(data);
      });
  }

}
