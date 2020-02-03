import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
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

  addRevision(revisionXml: string, processId: string, willComment: string) {
    const param = new HttpParams().append('processId', processId).append('willComment', willComment);
    return this.http.post(this.revisionUrl, revisionXml, {
      headers: authHttpOptions(this.authService.getToken()),
      responseType: 'text',
      params: param
    });
  }

  upload(data: File, processId: string, willComment: string) {
    return this.uploadService.uploadQuestionnaire(data, this.revisionUrl + 'upload', processId, willComment);
  }
}
