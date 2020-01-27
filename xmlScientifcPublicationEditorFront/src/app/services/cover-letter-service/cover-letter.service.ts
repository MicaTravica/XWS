import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth-service/auth.service';
import { authHttpOptions } from 'src/app/util/http-util';
import { environment } from 'src/environments/environment';
import { UploadDocumentsService } from '../upload-service/upload-documents.service';

@Injectable({
  providedIn: 'root'
})
export class CoverLetterService {
  
  private url: string;

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private uploadService: UploadDocumentsService
  ) {
    this.url = environment.restPath + '/coverLetter';
  }

  getCLTeplate() {
    return this.http.get(this.url + '/getCoverLetterTemplate',
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }

  addCL(clXml: string) {
    return this.http.post(this.url, clXml,
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }

  upload(file: File) {
    return this.uploadService.upload(file, this.url + '/upload');
  }
}
