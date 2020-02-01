import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PublicationService } from 'src/app/services/publication-service/publication.service';
import { ToastrService } from 'ngx-toastr';
import { HttpErrorResponse } from '@angular/common/http';
import { docSpec } from 'src/app/util/xonomy-editor-doc-spec/doc-spec-comments';

declare const Xonomy: any;

@Component({
  selector: 'app-add-comments',
  templateUrl: './add-comments.component.html',
  styleUrls: ['./add-comments.component.scss']
})
export class AddCommentsComponent implements OnInit {

  publicationXml = '';
  file: File;
  processId: string;

  constructor(
    private route: ActivatedRoute,
    private publicationService: PublicationService,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    const processId = this.route.snapshot.paramMap.get('processId');
    if (processId) {
      this.processId = processId;
      this.publicationService.getPublicationReviewerProcess(processId).subscribe(
        (data: string) => {
          this.publicationXml = data;
          this.publicationXml = this.publicationXml.split('ns1:anyAttr="anyValue"').join('');
          const editor = document.getElementById('editor');
          // tslint:disable-next-line: no-unused-expression
          new Xonomy.render(this.publicationXml, editor, docSpec);
      }
    );
    }
  }

  addPublication() {
    this.publicationXml = Xonomy.harvest() as string;
    this.publicationService.addCommentsProcess(this.publicationXml, this.processId).subscribe(
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
    this.publicationService.uploadCommentProcess(this.file, this.processId).subscribe(
      (data: string) => {
        this.toastr.success(data);
      }, (error: HttpErrorResponse) => {
        this.toastr.error(error.error);
      });
  }

}
