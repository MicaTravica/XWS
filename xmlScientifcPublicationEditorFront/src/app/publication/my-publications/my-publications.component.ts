import { Component, OnInit } from '@angular/core';
import { ProcessPSPService } from 'src/app/services/processPSP/process-psp.service';

@Component({
  selector: 'app-my-publications',
  templateUrl: './my-publications.component.html',
  styleUrls: ['./my-publications.component.scss']
})
export class MyPublicationsComponent implements OnInit {

  publications = [{ id: 'aaaaaaaa', name: 'aaaaaaaa', authors: 'aaaaaaaaaaaaaa' }];

  constructor(private processPSPService: ProcessPSPService) { }

  ngOnInit() {
    this.processPSPService.getMyPublications()
      .subscribe( res => {
      });
  }

  withdraw(id: number) {

  }

}
