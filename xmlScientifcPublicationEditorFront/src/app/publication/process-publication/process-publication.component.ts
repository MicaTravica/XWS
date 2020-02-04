import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProcessPSPService } from 'src/app/services/processPSP/process-psp.service';
import { PublicationService } from 'src/app/services/publication-service/publication.service';
import { OpenServiceService } from 'src/app/services/open-service/open-service.service';
import { RevisionService } from 'src/app/services/revision-service/revision.service';


declare var require: any;
const convert = require('xml-js');

@Component({
  selector: 'app-process-publication',
  templateUrl: './process-publication.component.html',
  styleUrls: ['./process-publication.component.scss']
})
export class ProcessPublicationComponent implements OnInit {

  reviewAssignments = [];
  id: string;

  constructor(
    private route: ActivatedRoute,
    private processService: ProcessPSPService,
    private publicationService: PublicationService,
    private openService: OpenServiceService,
    private revisionService: RevisionService
  ) { }

  ngOnInit() {
    // tslint:disable-next-line: no-string-literal
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.id = id;
      this.processService.getProcess(id).subscribe(
        (data: any) => {
          const obj = JSON.parse(convert.xml2json(data, { compact: true, spaces: 4 }));
          const version = obj['ns:version'] as any;
          const ra = (version['ns:reviewAssignments'].length) ? version['ns:reviewAssignments'] : [version['ns:reviewAssignments']];
          for (const r of ra) {
            if (r['ns:reviewAssigment']._attributes.state === 'accepted') {
              this.reviewAssignments.push({
                idQue: r['ns:reviewAssigment']['ns:review']['ns:idQuestionnaire']._text,
                idSPC: (r['ns:reviewAssigment']['ns:review']['ns:idScientificPublication']) ?
                  r['ns:reviewAssigment']['ns:review']['ns:idScientificPublication']._text : null,
              });
            }
          }
        }
      );
    }
  }

  accept() {
    const xml = this.setXml('published');
    this.cs(xml);
  }

  reject() {
    const xml = this.setXml('rejected');
    this.cs(xml);
  }

  revise() {
    const xml = this.setXml('revised');
    this.cs(xml);
  }

  cs(xml: string) {
    this.processService.changeState(xml).subscribe(
      (data: any) => {
        console.log(data);
      }
    );
  }

  setXml(st: string): string {
    const xmlJson = { processId: this.id, state: st };
    // tslint:disable-next-line: prefer-const
    let obj = { process: xmlJson };
    const retVal = convert.js2xml(obj, { compact: true, spaces: 4 });
    return retVal;
  }

  xmlCom(id: string) {
    this.publicationService.xmlVersion(id).subscribe(
      (data: any) => {
        this.openService.xml(data);
      }
    );
  }


  htmlCom(id: string) {
    this.publicationService.htmlVersion(id).subscribe(
      (data: any) => {
        this.openService.html(data);
      });
  }

  pdfCom(id: string) {
    this.publicationService.pdfVersion(id).subscribe(
      (data: any) => {
        this.openService.pdf(data);
      });
  }

  xmlQue(id: string) {
    this.revisionService.xml(id).subscribe(
      (data: any) => {
        this.openService.xml(data);
      }
    );
  }


  htmlQue(id: string) {
    this.revisionService.html(id).subscribe(
      (data: any) => {
        this.openService.html(data);
      });
  }

  pdfQue(id: string) {
    this.revisionService.pdf(id).subscribe(
      (data: any) => {
        this.openService.pdf(data);
      });
  }
}
