import { Component, OnDestroy } from '@angular/core';

@Component({
  selector: 'app-conversations',
  templateUrl: './conversations.component.html',
  styleUrls: ['../app.component.scss']
})
export class ConversationsComponent implements OnDestroy {
  constructor() {}

  ngOnDestroy(): void {
  }
}
