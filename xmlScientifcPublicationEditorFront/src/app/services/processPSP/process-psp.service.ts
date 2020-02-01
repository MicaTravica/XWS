import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from '../auth-service/auth.service';
import { environment } from 'src/environments/environment';
import { authHttpOptions } from 'src/app/util/http-util';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProcessPSPService {

  url: string;

  constructor(
    private http: HttpClient,
    private authService: AuthService) {
    this.url = environment.restPath + '/processPSP';
  }

  getPublicationsForPublishing() {
    return this.http.get(this.url + '/getForPublishing',
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }

  addReviewers(reviewers: string, id: string) {
    const param = new HttpParams().append('id', id);
    return this.http.post(this.url + '/addReviewers', reviewers,
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text',
        params: param
      });
  }

  getMyPublications() {
    return this.http.get(this.url + '/getMyPublication',
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }

  getProcess(id: string) {
    return this.http.get(this.url + '/' + id,
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }

  changeState(xml: string) {
    return this.http.post(this.url + '/changeState', xml,
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }

  getMyReviewAssigments() {
    return this.http.get(this.url + '/getMyReviewAssigments',
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }

   getMyProcess(id: string) {
    return this.http.get(this.url + '/myPSP/' + id,
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }


  getReviewAssigmentTemplate() {
    return this.http.get(this.url + '/acceptRejectAssigmentReviewTemplate',
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }

  acceptRejectAssigmentReview(acceptRejectData: string) {
    return this.http.post(this.url + '/acceptRejectAssigmentReview', acceptRejectData, 
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }

  

}
