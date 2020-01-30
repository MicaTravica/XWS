import { Component, OnInit } from '@angular/core';
import { ProcessPSPService } from 'src/app/services/processPSP/process-psp.service';

@Component({
  selector: 'app-for-revision',
  templateUrl: './for-revision.component.html',
  styleUrls: ['./for-revision.component.scss']
})
export class ForRevisionComponent implements OnInit {

  publications = [{ id: 'aaaaaaaa', name: 'aaaaaaaa', authors: 'aaaaaaaaaaaaaa' }];

  constructor(
      private processPSPService: ProcessPSPService) { }

  ngOnInit() {
    this.processPSPService.getMyReviewAssigments()
        .subscribe( res => {
          console.log(res);
        });
  }

  accept(id: number) {
    console.log(id);
  }

  reject(id: number) {
    console.log(id);
  }
}
