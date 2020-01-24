import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-for-revision',
  templateUrl: './for-revision.component.html',
  styleUrls: ['./for-revision.component.scss']
})
export class ForRevisionComponent implements OnInit {

  publications = [{ id: 'aaaaaaaa', name: 'aaaaaaaa', authors: 'aaaaaaaaaaaaaa' }];

  constructor() { }

  ngOnInit() {
  }

  accept(id: number) {
    console.log(id);
  }

  reject(id: number) {
    console.log(id);
  }
}
