import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from 'src/app/services/user-service/user.service';
import { Person } from 'src/app/models/user-model/user.model';
import { ProcessPSPService } from 'src/app/services/processPSP/process-psp.service';
import { ToastrService } from 'ngx-toastr';

declare var require: any;
const convert = require('xml-js');

@Component({
  selector: 'app-add-reviewers',
  templateUrl: './add-reviewers.component.html',
  styleUrls: ['./add-reviewers.component.scss']
})
export class AddReviewersComponent implements OnInit {

  reviewers = [];
  recommendedReviewers = [];
  selected = 0;
  chosenReviewers = [];
  id: string;
  xml: any;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private processService: ProcessPSPService,
    private toastr: ToastrService,
    private router: Router
  ) { }

  ngOnInit() {
    // tslint:disable-next-line: no-string-literal
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.id = id;
      this.userService.getReviewers().subscribe(
        (data: any) => {
          this.xml = JSON.parse(convert.xml2json(data, {compact: true, spaces: 4}));
          this.reviewers = this.xml['ns:persons']['ns:persons'] as Person[];
          this.userService.getRecommendedReviewers(this.id).subscribe( (recom: any) => {
            const xmlRecommended = JSON.parse(convert.xml2json(recom, {compact: true, spaces: 4}));

            if ( xmlRecommended['ns:persons']['ns:persons']) {
              if ( xmlRecommended['ns:persons']['ns:persons'].length) {
                this.recommendedReviewers = xmlRecommended['ns:persons']['ns:persons'] as Person[];
              } else {
                this.recommendedReviewers = [xmlRecommended['ns:persons']['ns:persons']];
              }
            }
          });
        }
      );
    }
  }

  addReviwer() {
    this.chosenReviewers.push(this.reviewers[this.selected]);
    this.reviewers.splice(this.selected, 1);
    this.selected = 0;
  }

  finish() {
    this.xml['ns:persons']['ns:persons'] = this.chosenReviewers;
    const value = convert.js2xml(this.xml, { compact: true, spaces: 4 });
    this.processService.addReviewers(value, this.id).subscribe(
      (data: any) => {
        this.toastr.success(data);
        this.router.navigate(['/publications']);
      }
    );
  }
}

