import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../auth-service/auth.service';
import { uploadAuthHttpOptions } from 'src/app/util/http-util';

@Injectable({
  providedIn: 'root'
})
export class UploadDocumentsService {

  constructor(private http: HttpClient,
              private authService: AuthService) { }

  upload(data: File, url: string) {
    const fd = new FormData();
    fd.append('file', data);
    return this.http.post(url, fd,
        {
          headers: uploadAuthHttpOptions(this.authService.getToken()),
          responseType: 'text',
        }
      );
  }
}
