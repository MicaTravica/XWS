import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import {httpOptions, authHttpOptions} from '../../util/http-util';
import { environment } from 'src/environments/environment';
import { AuthService } from '../auth-service/auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private usersUrl: string;

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) {
    this.usersUrl = environment.restPath + '/person';
  }

  public me() {
    const token = this.authService.getToken();
    return this.http.get(this.usersUrl,
      {
        headers: authHttpOptions(token),
        responseType: 'text'
      });
  }

  public getPersonTemplate(): any {
    return this.http.get(this.usersUrl + '/getPersonTemplate',
      {
        headers: httpOptions(),
        responseType: 'text'
      });
  }

  public getAuthTemplate(): any {
    return this.http.get(this.usersUrl + '/getAuthTemplate',
      {
        headers: httpOptions(),
        responseType: 'text'
      });
  }


  public save(userXML: string) {
    return this.http.post(environment.restPath + '/registration', userXML,
      {
        headers: httpOptions(),
        responseType: 'text'
      });
  }

  public getReviewers() {
    const token = this.authService.getToken();
    return this.http.get(this.usersUrl + '/reviewer',
      {
        headers: authHttpOptions(token),
        responseType: 'text'
      });
  }

  public savePerson(personXML: string) {
    const token = this.authService.getToken();
    return this.http.put(this.usersUrl, personXML,
      {
        headers: authHttpOptions(token),
        responseType: 'text'
      });
  }
}
