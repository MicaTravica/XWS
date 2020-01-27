import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth-service/auth.service';
import { environment } from 'src/environments/environment';
import { authHttpOptions } from 'src/app/util/http-util';

@Injectable({
  providedIn: 'root'
})
export class RevisionService {

  private revisionUrl: string;

  constructor(
    private http: HttpClient,
    private authService: AuthService) {
      this.revisionUrl = environment.restPath + '/questionnaire';
  }

  getQuestionnaireTemplate() {
    return this.http.get(this.revisionUrl + '/getQuestionnaireTemplate',
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }

  addRevision(revisionXml: string) {
    return this.http.post(this.revisionUrl, revisionXml, {
      headers: authHttpOptions(this.authService.getToken()),
      responseType: 'text'
    });
  }
}
