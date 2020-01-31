import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProcessPSPService } from 'src/app/services/processPSP/process-psp.service';

declare var require: any;
const convert = require('xml-js');

@Component({
  selector: 'app-see-revision',
  templateUrl: './see-revision.component.html',
  styleUrls: ['./see-revision.component.scss']
})
export class SeeRevisionComponent implements OnInit {

  versions = [];

  constructor(
    private route: ActivatedRoute,
    private processService: ProcessPSPService
  ) { }

  ngOnInit() {
    // tslint:disable-next-line: no-string-literal
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.processService.getMyProcess(id).subscribe(
        (data: any) => {
          const obj = JSON.parse(convert.xml2json(data, { compact: true, spaces: 4 }));
          const versions = (obj.processPSP.versions.version.length) ?  obj.processPSP.versions.version : [obj.processPSP.versions.version];
          for (const v of versions) {
            let comments = null;
            if (v.spc && v.spc.com) {
              comments = [];
              const spc = (v.spc.com.length) ? v.spc.com : [v.spc.com];
              for (const e of spc) {
                 comments.push(
                  { spcId: e._text }
                );
              }
            }
            this.versions.push(
              {
                version: v.number._text,
                spId: v.spId._text,
                spc: comments
              }
            );
          }
        }
      );
    }
  }

}
