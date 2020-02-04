import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PublicationService } from 'src/app/services/publication-service/publication.service';
import { OpenServiceService } from 'src/app/services/open-service/open-service.service';
import { HttpErrorResponse } from '@angular/common/http/http';

declare var require: any;
const convert = require('xml-js');

@Component({
  selector: 'app-search-publications',
  templateUrl: './search-publications.component.html',
  styleUrls: ['./search-publications.component.scss']
})
export class SearchPublicationsComponent implements OnInit {

  searchForm: FormGroup;
  publications = [];
  searchExecuted = false;

  constructor(
    private fb: FormBuilder,
    private publicationService: PublicationService,
    private openService: OpenServiceService
  ) {
    this.searchForm = this.fb.group({
      searchType: 'regular',
      search: ''
    });
  }

  ngOnInit() {
  }

  populateList(data: any) {
    this.publications = [];
    const obj = JSON.parse(convert.xml2json(data, { compact: true, spaces: 4 }));
    const result = obj.search as any;
    if (result.sp) {
      const sps = (result.sp.length) ? result.sp : [result.sp];
      this.searchExecuted = false;
      for (const sp of sps) {
        this.publications.push(
          {
            id: sp.id._text,
            name: sp.name._text,
            process: sp.process._text
          }
        );
      }
    }
  }


  onSubmit() {
    if (this.searchForm.value.searchType === 'regular') {
      this.doRegularSearch();
    } else {
      this.doAdvancedSearch();
    }
  }
  doRegularSearch() {
    this.searchExecuted = true;
    this.publicationService.reguralSearch(this.searchForm.value.search).subscribe(
      (data: any) => {
        this.populateList(data);
      }
    );
  }

  doAdvancedSearch() {
    this.searchExecuted = true;
    this.publicationService.metadataSearch(this.searchForm.value.search).subscribe(res => {
      this.populateList(res);
    },
    (err: HttpErrorResponse) => {
      console.log(err.message);
    });
  }

  getMetadataXML(scId: string) {
    this.publicationService.getMetadataXML(scId).subscribe(res => {
      this.openService.xml(res);
    });
  }

  getMetadataJSON(scId: string) {
    this.publicationService.getMetadataJSON(scId).subscribe( res => {
      this.openService.json(res);
    });
  }

  xml(id: string) {
    this.publicationService.xml(id).subscribe(
      (data: any) => {
        this.openService.xml(data);
      }
    );
  }


  html(id: string) {
    this.publicationService.html(id).subscribe(
      (data: any) => {
        this.openService.html(data);
      });
  }

  pdf(id: string) {
    this.publicationService.pdf(id).subscribe(
      (data: any) => {
        this.openService.pdf(data);
      });
  }

}

