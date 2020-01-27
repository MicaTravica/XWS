import { Component, OnInit } from '@angular/core';
import { CoverLetterService } from '../services/cover-letter-service/cover-letter.service';
import { docSpec } from '../util/xonomy-editor-doc-spec/doc-spec-coverLetter';
declare const Xonomy: any;

@Component({
  selector: 'app-add-cover-letter',
  templateUrl: './add-cover-letter.component.html',
  styleUrls: ['./add-cover-letter.component.scss']
})
export class AddCoverLetterComponent implements OnInit {

  clXml = '';

  constructor(private clService: CoverLetterService) { }

  ngOnInit() {

    this.clService.getCLTeplate().subscribe(
      (data: string) => {
        this.clXml = data;
        this.clXml = this.clXml.split('ns1:anyAttr="anyValue"').join('');
        const editor = document.getElementById('editor');
        // tslint:disable-next-line: no-unused-expression
        new Xonomy.render(this.clXml, editor, docSpec);
      });
  }

  addCL() {
    this.clXml = Xonomy.harvest() as string;
    this.clService.addCL(this.clXml)
      .subscribe(res => {
        console.log(res);
      });
  }

}
