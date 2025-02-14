import { Component, OnDestroy } from '@angular/core';
import { BehaviorSubject, catchError, interval, mergeMap, Observable, of, Subject, switchMap, takeUntil, tap } from 'rxjs';
import { Conversation } from '../interfaces/conversation.interface';
import { ConversationsService } from '../conversations.service';
import { Router } from '@angular/router';
import { UsersService } from '../users.service';

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

  private usersSubject = new BehaviorSubject<any[]>([]);
  public users$ = this.usersSubject.asObservable();
  public users: any[] = [];

  constructor(
    private conversationService: ConversationsService,
    private usersService: UsersService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (!localStorage.getItem('token')) {
      window.location.href = '/login';
    }

    this.getConversations().pipe(
      mergeMap(() => this.getUsers())
    ).subscribe();

    interval(500)
    .pipe(
      switchMap(() => this.conversationService.getConversations()),
      takeUntil(this.destroy$)
    )
    .subscribe((response: Conversation[]) => {
      this.conversationsSubject.next(response);
      this.conversations = response;
    });
  }

  getConversations(): Observable<Conversation[]> {
    return this.conversationService.getConversations().pipe(
      tap((response: Conversation[]) => {
        this.conversationsSubject.next(response);
        this.conversations = response;
      }),
      catchError((error) => {
        console.error(error);
        return of([]);
      }),
      takeUntil(this.destroy$)
    );
  }

  getConversationName(conversation: Conversation): string {
    const currentUserEmail = localStorage.getItem('email');
    const user1 = conversation.user1Id.email !== currentUserEmail ? `${conversation.user1Id.firstName} ${conversation.user1Id.lastName}` : '';
    const user2 = conversation.user2Id.email !== currentUserEmail ? `${conversation.user2Id.firstName} ${conversation.user2Id.lastName}` : '';
    return user1 || user2;
  }

  goToMessages(conversationId: number): void {
    this.router.navigate([`/conversation`, conversationId, 'messages']);
  }

  getUsers(): Observable<any[]> {
    return this.usersService.getUsers().pipe(
      tap((response: any[]) => {
        const currentUserEmail = localStorage.getItem('email');
        const conversations = this.conversations;
        response = response.filter((user) => {
          const isUserInConversation = conversations.some((conversation) => {
            return conversation.user1Id.email === user.email || conversation.user2Id.email === user.email;
          });
          return user.email !== currentUserEmail && !isUserInConversation;
        });
        this.usersSubject.next(response);
        this.users = response;
      }),
      catchError((error) => {
        console.error(error);
        return of([]);
      }),
      takeUntil(this.destroy$)
    );
  }

  startConversation(userId: number): void {
    this.conversationService.startConversation(userId).pipe(
      tap(() => {
        this.getConversations().pipe(
          mergeMap(() => this.getUsers())
        ).subscribe();
      }),
      catchError((error) => {
        console.error(error);
        return of([]);
      }),
      takeUntil(this.destroy$)
    ).subscribe();
  }

  logout() {
    localStorage.removeItem('token');
    window.location.href = '/login';
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
