import { HttpHeaders } from '@angular/common/http';

export const httpOptions = () => {
    return new HttpHeaders()
        .append('Content-Type', 'application/xml')
        .append('Accept', 'application/xml');
};

export const authHttpOptions = (token) => {
    return new HttpHeaders()
            .append('Content-Type', 'application/xml')
            .append('Accept', 'application/xml')
            .append('Authorization', 'Bearer' + token);
};
