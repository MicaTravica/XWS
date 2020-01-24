import { HttpHeaders } from '@angular/common/http';

export const httpOptions = {
    headers: new HttpHeaders({
    'Content-Type': 'application/xml',
    'Accept': 'application/xml'
    })
};

export const authHttpOptions = (token) => {
    return {
        headers: new HttpHeaders({
        'Content-Type': 'application/xml',
        'Accept': 'application/xml',
        'Authorization': 'Bearer ' + token
        })
    };
};
