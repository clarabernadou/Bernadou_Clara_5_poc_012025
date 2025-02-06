import { Component, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../auth.service';
import { FormBuilder, Validators } from '@angular/forms';
import { catchError, Subject, takeUntil, tap } from 'rxjs';
import { AuthToken, Register } from '../interfaces/auth.interface';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['../app.component.scss']
})
export class RegisterComponent implements OnDestroy {
  private destroy$ = new Subject<void>();
  public errorMessage: string = '';

  public form = this.fb.group({
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.required]],
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
    const registerRequest: Register = this.form.value as Register;
    this.authService.register(registerRequest).pipe(
      tap((response: AuthToken) => {
        localStorage.setItem('email', registerRequest.email);
        localStorage.setItem('token', response.token);
        this.router.navigate(['/conversations']);
      }),
      catchError((error) => {
        this.errorMessage = error.error.message;
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