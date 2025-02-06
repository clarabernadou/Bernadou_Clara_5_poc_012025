import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Conversation } from './interfaces/conversation.interface';

@Injectable({
  providedIn: 'root'
})
export class ConversationsService {
  constructor(private http: HttpClient) { }

  getConversations(): Observable<Conversation[]> {
    const token = localStorage.getItem('token');

    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });

    return this.http.get<Conversation[]>('http://localhost:8080/api/auth/user/conversations', { headers });
  }
}
