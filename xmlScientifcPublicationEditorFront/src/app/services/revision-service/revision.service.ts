import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth-service/auth.service';
import { environment } from 'src/environments/environment';
import { authHttpOptions, uploadAuthHttpOptions } from 'src/app/util/http-util';
import { UploadDocumentsService } from '../upload-service/upload-documents.service';

@Injectable({
  providedIn: 'root'
})
export class RevisionService {

  public revisionUrl: string;

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private uploadService: UploadDocumentsService) {
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

  upload(data: File) {
    return this.uploadService.upload(data, this.revisionUrl + 'upload');
  }
}
