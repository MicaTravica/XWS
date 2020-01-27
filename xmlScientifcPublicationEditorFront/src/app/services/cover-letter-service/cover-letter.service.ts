import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth-service/auth.service';
import { authHttpOptions } from 'src/app/util/http-util';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CoverLetterService {
  private url: string;

  constructor(
    private http: HttpClient,
    private authService: AuthService
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
}
