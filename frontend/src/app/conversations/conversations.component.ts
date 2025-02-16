import { Component, OnDestroy, OnInit } from '@angular/core';
import { BehaviorSubject, catchError, forkJoin, interval, Observable, of, Subject, takeUntil, tap } from 'rxjs';
import { Conversation } from '../interfaces/conversation.interface';
import { ConversationsService } from '../conversations.service';
import { Router } from '@angular/router';
import { UsersService } from '../users.service';

@Component({
  selector: 'app-conversations',
  templateUrl: './conversations.component.html',
  styleUrls: ['../app.component.scss']
})
export class ConversationsComponent implements OnInit, OnDestroy {
  private destroy$ = new Subject<void>();

  private conversationsSubject = new BehaviorSubject<Conversation[]>([]);
  public conversations$ = this.conversationsSubject.asObservable();
  public conversations: Conversation[] = [];

  private usersSubject = new BehaviorSubject<any[]>([]);
  public users$ = this.usersSubject.asObservable();
  public users: any[] = [];
  private currentUserEmail: string = '';

  constructor(
    private conversationService: ConversationsService,
    private usersService: UsersService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (!localStorage.getItem('token')) {
      window.location.href = '/login';
    }
    this.currentUserEmail = localStorage.getItem('email') || '';
    this.refreshData();

    interval(500)
    .pipe(takeUntil(this.destroy$))
    .subscribe(() => this.refreshData());
  }

  private refreshData(): void {
    forkJoin({
      conversations: this.conversationService.getConversations().pipe(
        catchError((error) => {
          console.error(error);
          return of([]);
        })
      ),
      users: this.usersService.getUsers().pipe(
        catchError((error) => {
          console.error(error);
          return of([]);
        })
      )
    }).subscribe(({ conversations, users }) => {
      this.conversations = conversations;
      this.conversationsSubject.next(conversations);

      const filteredUsers = users.filter(user => {
        const isUserInConversation = conversations.some(conv =>
          conv.user1Id.email === user.email || conv.user2Id.email === user.email
        );
        return user.email !== this.currentUserEmail && !isUserInConversation;
      });
      this.users = filteredUsers;
      this.usersSubject.next(filteredUsers);
    });
  }

  getConversationName(conversation: Conversation): string {
    const user1 = conversation.user1Id.email !== this.currentUserEmail ? `${conversation.user1Id.firstName} ${conversation.user1Id.lastName}` : '';
    const user2 = conversation.user2Id.email !== this.currentUserEmail ? `${conversation.user2Id.firstName} ${conversation.user2Id.lastName}` : '';
    return user1 || user2;
  }

  goToMessages(conversationId: number): void {
    this.router.navigate([`/conversation`, conversationId, 'messages']);
  }

  startConversation(userId: number): void {
    this.conversationService.startConversation(userId).pipe(
      tap(() => this.refreshData()),
      catchError((error) => {
        console.error(error);
        return of([]);
      }),
      takeUntil(this.destroy$)
    ).subscribe();
  }

  logout(): void {
    localStorage.removeItem('token');
    window.location.href = '/login';
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}