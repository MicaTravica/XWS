import { Component, OnInit } from '@angular/core';
import { PublicationService } from 'src/app/services/publication-service/publication.service';
import { docSpec } from '../../util/xonomy-editor-doc-spec/doc-spec-publication';
import { HttpErrorResponse } from '@angular/common/http';
import { ToastrService } from 'ngx-toastr';

declare const Xonomy: any;

@Component({
  selector: 'app-add-publication',
  templateUrl: './add-publication.component.html',
  styleUrls: ['./add-publication.component.scss']
})
export class AddPublicationComponent implements OnInit {

  publicationXml = '';
  file: File;

  constructor(
    private publicationService: PublicationService,
    private toastr: ToastrService
  ) { }

  ngOnInit() {
    this.publicationService.getPublicationTemplate().subscribe(
      (data: string) => {
        this.publicationXml = data;
        this.publicationXml = this.publicationXml.split('ns1:anyAttr="anyValue"').join('');
        const editor = document.getElementById('editor');
        // tslint:disable-next-line: no-unused-expression
        new Xonomy.render(this.publicationXml, editor, docSpec);
      }
    );
  }

  addPublication() {
    this.publicationXml = Xonomy.harvest() as string;
    this.publicationService.addPublication(this.publicationXml).subscribe(
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
    this.publicationService.upload(this.file).subscribe(
      (data: string) => {
        this.toastr.success(data);
      }, (error: HttpErrorResponse) => {
        this.toastr.error(error.error);
      });
  }
}
