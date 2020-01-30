import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProcessPSPService } from 'src/app/services/processPSP/process-psp.service';

@Component({
  selector: 'app-see-revision',
  templateUrl: './see-revision.component.html',
  styleUrls: ['./see-revision.component.scss']
})
export class SeeRevisionComponent implements OnInit {

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
          console.log(data);
          // const obj = JSON.parse(convert.xml2json(data, { compact: true, spaces: 4 }));
          // const version = obj['ns:version'] as any;
          // const ra = (version['ns:reviewAssignments'].length) ? version['ns:reviewAssignments'] : [version['ns:reviewAssignments']];
          // for (const r of ra) {
          //   if (r['ns:reviewAssigment']._attributes.state === 'accepted') {
          //     this.reviewAssignments.push({
          //       idQue: r['ns:reviewAssigment']['ns:review']['ns:idQuestionnaire']._text,
          //       idSPC: (r['ns:reviewAssigment']['ns:review']['ns:idScientificPublication']) ?
          //         r['ns:reviewAssigment']['ns:review']['ns:idScientificPublication']._text : null,
          //     });
          //   }
          // }
        }
      );
    }
  }

}
