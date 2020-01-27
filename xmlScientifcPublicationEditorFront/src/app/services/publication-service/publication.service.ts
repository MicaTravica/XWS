import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { authHttpOptions } from 'src/app/util/http-util';
import { AuthService } from '../auth-service/auth.service';

@Injectable({
  providedIn: 'root'
})
export class PublicationService {

  private usersUrl: string;

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {
    this.usersUrl = environment.restPath + '/scientificPublication';
  }

  getPublicationTemplate() {
    return this.http.get(this.usersUrl + '/getSPTemplate',
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }
  addPublication(publication: string) {
    return this.http.post(this.usersUrl, publication,
      {
        headers: authHttpOptions(this.authService.getToken()),
        responseType: 'text'
      });
  }
}
