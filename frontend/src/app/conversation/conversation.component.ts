import { Component, OnDestroy, OnInit } from '@angular/core';
import { ConversationsService } from '../conversations.service';
import { Conversation, Message } from '../interfaces/conversation.interface';
import { BehaviorSubject, catchError, map, Observable, Subject, takeUntil, tap, of } from 'rxjs';
import { MessagesService } from '../messages.service';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-conversation',
  templateUrl: './conversation.component.html',
  styleUrls: ['../app.component.scss']
})
export class ConversationComponent implements OnInit, OnDestroy {
  public userName: string = '';
  private messagesSubject = new BehaviorSubject<Message[]>([]);
  public messages$ = this.messagesSubject.asObservable();
  public messages: Message[] = [];
  private destroy$ = new Subject<void>();

  public newMessage = this.fb.group({
    content: ['', Validators.required]
  });

  constructor(
    private conversationService: ConversationsService,
    private messagesService: MessagesService,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    if (!localStorage.getItem('token')) {
      window.location.href = '/login';
    }

    this.getUserName().subscribe((name) => {
      this.userName = name;
    });

    this.getMessages();
  }

  goToConversations(): void {
    window.location.href = '/conversations';
  }

  getUserName(): Observable<string> {
    const url = window.location.href;
    const urlParts = url.split('/');
    const conversationId = parseInt(urlParts[urlParts.length - 2]);

    return this.conversationService.getConversations().pipe(
      map((conversations: Conversation[]) => {
        const conversation = conversations.find(conv => conv.id === conversationId);
        const currentUserEmail = localStorage.getItem('email');

        const user1 = conversation?.user1Id;
        const user2 = conversation?.user2Id;
        return user1?.email !== currentUserEmail 
          ? `${user1?.firstName} ${user1?.lastName}` 
          : `${user2?.firstName} ${user2?.lastName}` || ''; 
      })
    );
  }

  getMessages(): void {
    this.messagesService.getMessages().pipe(
      tap((response: Message[]) => {
        this.messagesSubject.next(response);
        this.messages = response;
      }),
      catchError((error) => {
        return error;
      }),
      takeUntil(this.destroy$)
    ).subscribe();
  }

  itsMyMessage(message: Message): boolean {
    const currentUserEmail = localStorage.getItem('email');
    return message.userId?.email === currentUserEmail;
  }

  sendMessage(): void {
    if (this.newMessage.invalid) return;
    const sendMessageRequest = this.newMessage.value as Message;
    this.messagesService.sendMessage(sendMessageRequest).pipe(
      tap(() => {
        this.getMessages();
        this.newMessage.reset();
      }),
      catchError((error) => {
        console.error(error);
        return of([]);
      }),
      takeUntil(this.destroy$)
    ).subscribe();
  }  

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}