import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { User } from '../../models/user-model/user.model'
import { Router } from '@angular/router';
import {httpOptions, authHttpOptions} from '../../util/http-util';
import {registrationTemplate} from '../../util/xml-templates/registration-template';
import { LoginComponent } from 'src/app/core/login/login.component';
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

  public me(token: string) {
    return this.http.get(this.usersUrl + '/userme',
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
      })
    .subscribe(() => {
      this.router.navigate(['/login']);
    });
  }

  public getUserFromLocalStorage() {
    let user: User = new User();
    const u = localStorage.getItem('user');
    if (!u) {
      const token = this.authService.getToken();
      this.me(token).subscribe(
      data => {
        localStorage.setItem('user', JSON.stringify(data));
      });
    }
    user = user.deserialize(u);
    return user;
  }
}
