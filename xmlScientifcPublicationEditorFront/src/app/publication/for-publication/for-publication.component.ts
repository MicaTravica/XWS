import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-for-publication',
  templateUrl: './for-publication.component.html',
  styleUrls: ['./for-publication.component.scss']
})
export class ForPublicationComponent implements OnInit {

  publications = [{ id: 'aaaaaaaa', name: 'aaaaaaaa', authors: 'aaaaaaaaaaaaaa' }];

  constructor(private router: Router) { }

  ngOnInit() {
  }

  process(id: number) {
    this.router.navigate(['/process/' + id]);
  }
}
