import { Component, OnInit } from '@angular/core';
import { RevisionService } from 'src/app/services/revision-service/revision.service';
import { docSpec} from 'src/app/util/xonomy-editor-doc-spec/doc-spec-questionnaire';
import { HttpClient } from '@angular/common/http';
declare const Xonomy: any;

@Component({
  selector: 'app-add-revision',
  templateUrl: './add-revision.component.html',
  styleUrls: ['./add-revision.component.scss']
})
export class AddRevisionComponent implements OnInit {

  revisionXml = '';
  file: File = null;

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

  onFileSelected(event: any) {
    this.file = event.target.files[0];
  }

  onUpload() {
    this.revisionService.upload(this.file).subscribe(res => {console.log(res); });
  }

}
