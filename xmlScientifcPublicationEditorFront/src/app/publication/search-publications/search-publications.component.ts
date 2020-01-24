import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-search-publications',
  templateUrl: './search-publications.component.html',
  styleUrls: ['./search-publications.component.scss']
})
export class SearchPublicationsComponent implements OnInit {

  searchForm: FormGroup;
  publications = [{ id: 'aaaaaaaa', name: 'aaaaaaaa', authors: 'aaaaaaaaaaaaaa' }];

  constructor(fb: FormBuilder) {
    this.searchForm = fb.group({
      searchType: 'regular',
      search: ''
    });
  }

  ngOnInit() {
  }

  onSubmit() {
    if (this.searchForm.value.searchType === 'regular') {
      this.doRegularSearch();
    } else {
      this.doAdvancedSearch();
    }
  }
  doRegularSearch() {
    console.log(this.searchForm.value.search);
  }

  doAdvancedSearch() {
    console.log(this.searchForm.value.search);
  }
}

