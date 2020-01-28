import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
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

  upload(data: File) {
    return this.uploadService.upload(data, this.url + '/upload');
  }
}
