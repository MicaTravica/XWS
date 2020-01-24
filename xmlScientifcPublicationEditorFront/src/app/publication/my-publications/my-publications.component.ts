import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-my-publications',
  templateUrl: './my-publications.component.html',
  styleUrls: ['./my-publications.component.scss']
})
export class MyPublicationsComponent implements OnInit {

  publications = [{ id: 'aaaaaaaa', name: 'aaaaaaaa', authors: 'aaaaaaaaaaaaaa' }];

  constructor() { }

  ngOnInit() {
  }

  withdraw(id: number) {

  }

}
