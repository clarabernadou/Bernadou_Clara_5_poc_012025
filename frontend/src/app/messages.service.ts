import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Message } from './interfaces/conversation.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MessagesService {
  constructor(private http: HttpClient) {}

  getMessages(): Observable<Message[]> {
    const url = window.location.href;
    const urlParts = url.split('/');
    const id = parseInt(urlParts[urlParts.length - 2]);

    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.http.get<Message[]>(`http://localhost:8080/api/auth/conversation/${id}/messages`, { headers });
  }

  sendMessage(message: Message): Observable<Message> {
    const url = window.location.href;
    const urlParts = url.split('/');
    const id = parseInt(urlParts[urlParts.length - 2]);

    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.http.post<Message>(`http://localhost:8080/api/auth/conversation/${id}/message`, message, { headers });
  }
}
