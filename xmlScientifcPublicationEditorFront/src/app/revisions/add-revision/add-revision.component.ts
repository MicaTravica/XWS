import { Component, OnInit } from '@angular/core';
import { RevisionService } from 'src/app/services/revision-service/revision.service';
import { docSpec} from 'src/app/util/xonomy-editor-doc-spec/doc-spec-questionnaire';
declare const Xonomy: any;

@Component({
  selector: 'app-add-revision',
  templateUrl: './add-revision.component.html',
  styleUrls: ['./add-revision.component.scss']
})
export class AddRevisionComponent implements OnInit {

  revisionXml = '';

  constructor(private revisionService: RevisionService) { }

  ngOnInit() {
    this.revisionService.getQuestionnaireTemplate()
      .subscribe( (template: string) => {
          this.revisionXml = template;
          this.revisionXml = this.revisionXml.split('ns1:anyAttr="anyValue"').join('');
          const editor = document.getElementById('editor');
          // tslint:disable-next-line: no-unused-expression
          new Xonomy.render(this.revisionXml, editor, docSpec);
      });
  }


  addRevision() {
    this.revisionXml = Xonomy.harvest() as string;
    console.log(this.revisionXml);
    this.revisionService.addRevision(this.revisionXml)
      .subscribe( res => {
          console.log(res);
        }
      );
  }

}
