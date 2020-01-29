import { Component, OnInit } from '@angular/core';
import { CoverLetterService } from '../services/cover-letter-service/cover-letter.service';
import { docSpec } from '../util/xonomy-editor-doc-spec/doc-spec-coverLetter';
import { ToastrService } from 'ngx-toastr';
import { HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Route } from '@angular/compiler/src/core';
declare const Xonomy: any;

@Component({
  selector: 'app-add-cover-letter',
  templateUrl: './add-cover-letter.component.html',
  styleUrls: ['./add-cover-letter.component.scss']
})
export class AddCoverLetterComponent implements OnInit {

  clXml = '';
  file: File;
  processId: string;

  constructor(
    private clService: CoverLetterService,
    private toastr: ToastrService,
    private route: ActivatedRoute,
    private router: Router
  ) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.processId = params.processId;
      this.clService.getCLTeplate().subscribe(
        (data: string) => {
          this.clXml = data;
          this.clXml = this.clXml.split('ns1:anyAttr="anyValue"').join('');
          const editor = document.getElementById('editor');
          // tslint:disable-next-line: no-unused-expression
          new Xonomy.render(this.clXml, editor, docSpec);
        });
    });
  }

  addCL() {
    this.clXml = Xonomy.harvest() as string;
    this.clService.addCL(this.clXml, this.processId).subscribe(
      (data: string) => {
        this.toastr.success(data);
      }, (error: HttpErrorResponse) => {
        this.toastr.error(error.error);
      },
      () => {
          this.router.navigate(['my_publications']);
        }
      );
  }

  onFileSelected(event: any) {
    this.file = event.target.files[0];
  }

  onUpload() {
    this.clService.upload(this.file, this.processId).subscribe(
      (data: string) => {
        this.toastr.success(data);
      }, (error: HttpErrorResponse) => {
        this.toastr.error(error.error);
      },
      () => {
        this.router.navigate(['my_publications']);
      }
    );
  }
}
