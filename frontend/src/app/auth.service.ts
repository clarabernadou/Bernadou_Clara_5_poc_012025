import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Login, Register } from './interfaces/auth.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  register(data: Register): Observable<any> {
    return this.http.post('http://localhost:8080/api/register', data);
  }

  login(data: Login): Observable<any> {
    return this.http.post('http://localhost:8080/api/login', data);
  }
}
