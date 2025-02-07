import { Component, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { FormBuilder, Validators } from '@angular/forms';
import { catchError, Subject, takeUntil, tap } from 'rxjs';
import { AuthToken, Login } from '../interfaces/auth.interface';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../app.component.scss']
})
export class LoginComponent implements OnDestroy {
  private destroy$ = new Subject<void>();

  public form = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(3)]]
  });

  constructor(
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder,
  ) {}

  ngOnInit(): void {
    if (localStorage.getItem('token')) {
      window.location.href = '/conversations';
    }
  }

  onSubmit() {
    if (this.form.invalid) return;
    const loginRequest: Login = this.form.value as Login;
    this.authService.login(loginRequest).pipe(
      tap((response: AuthToken) => {
        localStorage.setItem('email', loginRequest.email);
        localStorage.setItem('token', response.token);
        this.router.navigate(['/conversations']);
      }),
      catchError((error) => {
        return error;
      }),
      takeUntil(this.destroy$)
    ).subscribe();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

}