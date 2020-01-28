import { HttpClient } from '@angular/common/http';
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

  getMyPublications() {
    return new Observable();
  }
}
