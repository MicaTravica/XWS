import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OpenServiceService {

  constructor() { }

  xml(data: any) {
    const blob = new Blob([data], {type: 'text/xml'});
    const url = URL.createObjectURL(blob);
    window.open(url);
  }

  html(data: any) {
    const blob = new Blob([data], {type: 'text/html'});
    const url = URL.createObjectURL(blob);
    window.open(url);
  }

  pdf(data: any) {
    const blob = new Blob([data], { type: 'application/pdf' });
    const url = URL.createObjectURL(blob);
    window.open(url);
  }

  json(data: any) {
    const blob = new Blob([data], { type: 'text/json' });
    const url = URL.createObjectURL(blob);
    window.open(url);
  }
}
