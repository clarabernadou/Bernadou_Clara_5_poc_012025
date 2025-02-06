import { Component, OnDestroy } from '@angular/core';
import { BehaviorSubject, catchError, mergeMap, Subject, takeUntil, tap } from 'rxjs';
import { Conversation } from '../interfaces/conversation.interface';
import { ConversationsService } from '../conversations.service';

@Component({
  selector: 'app-conversations',
  templateUrl: './conversations.component.html',
  styleUrls: ['../app.component.scss']
})
export class ConversationsComponent implements OnDestroy {
  private destroy$ = new Subject<void>();
  private conversationsSubject = new BehaviorSubject<Conversation[]>([]);
  public conversations$ = this.conversationsSubject.asObservable();
  public conversations: Conversation[] = [];

  constructor(
    private conversationService: ConversationsService,
  ) {}

  ngOnInit(): void {
    if (!localStorage.getItem('token')) {
      window.location.href = '/login';
    }
    this.getConversations();
  }

  getConversations(): void {
    this.conversationService.getConversations().pipe(
      tap((response: Conversation[]) => {
        this.conversationsSubject.next(response);
        this.conversations = response;
      }),
      catchError((error) => {
        return error;
      }),
      takeUntil(this.destroy$)
    ).subscribe();
  }

  getConversationName(conversation: Conversation): string {
    const currentUserEmail = localStorage.getItem('email');
    const user1 = conversation.user1Id.email !== currentUserEmail ? `${conversation.user1Id.firstName} ${conversation.user1Id.lastName}` : '';
    const user2 = conversation.user2Id.email !== currentUserEmail ? `${conversation.user2Id.firstName} ${conversation.user2Id.lastName}` : '';
    return user1 || user2;
  }

  logout() {
    localStorage.removeItem('token');
    window.location.href = '/login';
  }

  ngOnDestroy(): void {}
}
