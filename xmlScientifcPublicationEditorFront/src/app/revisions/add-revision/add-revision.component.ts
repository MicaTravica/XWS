import { Component, OnInit } from '@angular/core';
import { RevisionService } from 'src/app/services/revision-service/revision.service';
import { docSpec} from 'src/app/util/xonomy-editor-doc-spec/doc-spec-questionnaire';
import { ToastrService } from 'ngx-toastr';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
declare const Xonomy: any;

@Component({
  selector: 'app-add-revision',
  templateUrl: './add-revision.component.html',
  styleUrls: ['./add-revision.component.scss']
})
export class AddRevisionComponent implements OnInit {

  revisionXml = '';
  file: File;
  processId = '';

  constructor(
    private revisionService: RevisionService,
    private toastr: ToastrService,
    private route: ActivatedRoute,
  ) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.processId = params.processId;
      this.revisionService.getQuestionnaireTemplate().subscribe( (template: string) => {
          this.revisionXml = template;
          this.revisionXml = this.revisionXml.split('ns1:anyAttr="anyValue"').join('');
          const editor = document.getElementById('editor');
          // tslint:disable-next-line: no-unused-expression
          new Xonomy.render(this.revisionXml, editor, docSpec);
      });
    });
  }

  addRevision() {
    this.revisionXml = Xonomy.harvest() as string;
    this.revisionService.addRevision(this.revisionXml, this.processId).subscribe(
      (data: string) => {
        this.toastr.success(data);
      }, (error: HttpErrorResponse) => {
        this.toastr.error(error.error);
      });
  }

  onFileSelected(event: any) {
    this.file = event.target.files[0];
  }

  onUpload() {
    this.revisionService.upload(this.file,  this.processId).subscribe(
      (data: string) => {
        this.toastr.success(data);
      }, (error: HttpErrorResponse) => {
        this.toastr.error(error.error);
      });
  }

}
