import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { PublicationService } from 'src/app/services/publication-service/publication.service';
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

  constructor(
    private fb: FormBuilder,
    private publicationService: PublicationService
  ) {
    this.searchForm = fb.group({
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
      for (const sp of sps) {
        this.publications.push(
          {
            id: sp.id._text,
            name: sp.name._text
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
    this.publicationService.reguralSearch(this.searchForm.value.search).subscribe(
      (data: any) => {
        this.populateList(data);
      }
    );
  }

  doAdvancedSearch() {
    this.publicationService.metadataSearch(this.searchForm.value.search).subscribe(res => {
      this.populateList(res);
    },
    (err: HttpErrorResponse) => {
      console.log(err.message);
    });
  }
}

