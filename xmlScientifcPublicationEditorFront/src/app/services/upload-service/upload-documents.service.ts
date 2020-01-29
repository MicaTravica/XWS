import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { AuthService } from '../auth-service/auth.service';
import { uploadAuthHttpOptions } from 'src/app/util/http-util';

@Injectable({
  providedIn: 'root'
})
export class UploadDocumentsService {

  constructor(private http: HttpClient,
              private authService: AuthService) { }

  upload(data: File, url: string, id: string) {
    // const paramas = new HttpParams().append('processId', id);
    const fd = new FormData();
    fd.append('file', data);
    fd.append('processId', id);
    return this.http.post(url, fd,
        {
          headers: uploadAuthHttpOptions(this.authService.getToken()),
          responseType: 'text',
          // params: paramas
        }
      );
  }
}
