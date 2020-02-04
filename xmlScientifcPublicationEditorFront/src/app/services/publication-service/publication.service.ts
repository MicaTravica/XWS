import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpParams } from '@angular/common/http';
import { authHttpOptions } from 'src/app/util/http-util';
import { AuthService } from '../auth-service/auth.service';
import { UploadDocumentsService } from '../upload-service/upload-documents.service';

@Injectable({
  providedIn: 'root'
})
export class PublicationService {

  private url: string;

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private uploadService: UploadDocumentsService
  ) {
    this.url = environment.restPath + '/scientificPublication';
  }

  getPublicationTemplate() {
    return this.http.get(this.url + '/getSPTemplate',
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }

  addPublication(publication: string) {
    return this.http.post(this.url, publication,
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }

  upload(data: File, processId: string) {
    return this.uploadService.upload(data, this.url + '/upload', processId);
  }

  uploadNewVersion(file: File, processId: string) {
    return this.uploadService.upload(file, this.url + '/upload/newVersion', processId);
  }

  getMyPublication(spId: string) {
    return this.http.get(this.url + '/notPub/' + spId,
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }

  addNewVersionPublication(publicationXml: string, processId: string) {
     return this.http.post(this.url + '/' + processId, publicationXml,
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }

  reguralSearch(value: string) {
    const param = new HttpParams().append('param', value);
    return this.http.get(this.url + '/search',
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text',
        params: param
      });
  }

  getPublicationReviewerProcess(processId: string) {
    return this.http.get(this.url + '/review/' + processId,
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }

  addCommentsProcess(publicationXml: string, processId: string) {
    return this.http.post(this.url + '/review/' + processId, publicationXml,
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }

  uploadCommentProcess(file: File, processId: string) {
    return this.uploadService.upload(file, this.url + '/upload/review', processId);
  }

  getMetadata(spId: string) {
    return this.http.get(this.url + '/getMetadata/' + spId,
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }

}
