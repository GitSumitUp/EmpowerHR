import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
// import { environment } from '../environments/environment';


@Injectable({
  providedIn: 'root'
})


export class WebclientService {

  private base_url =  'http://localhost:8080';
  private token: string | null = null;
  private loggedInUserId: string | null = null;
  loading: boolean = false;

  constructor(private http: HttpClient) {
    let token = localStorage.getItem("token");
    if (token) {
      this.setToken(token);
    }
  }

  public setToken(token: string | null) {
    this.token = token;
  }

  public getToken(): string
  {
    return this.token??'';
  }

  // public get<T>(url: string, p0?: { responseType: string }): Observable<T> {
  //   return this.http.get<T>(`${this.base_url}${url}`, {});
  // }

  public get<T>(url: string, p0?: { responseType: string; }) {
    return this.http.get<T>(`${this.base_url}${url}`, {
      headers: {
        TOKEN: this.token ?? ''
      }
    });
  }

  // public getOne<T>(url: string): Observable<T> {
  //   return this.http.get<T>(`${this.base_url}${url}`, {});
  // }

  public getOne<T>(url: string) {
    return this.http.get<T>(`${this.base_url}${url}`, {
      headers: {
        TOKEN: this.token ?? ''
      }
    });
  }

  // public delete<T>(url: string): Observable<T> {
  //   return this.http.delete<T>(`${this.base_url}${url}`, {

  //   });
  // }

  public delete<T>(url: string) {
    return this.http.delete<T>(`${this.base_url}${url}`, {
      headers: {
        TOKEN: this.token ?? ''
      }
    });
  }

  // public post<T, U>(url: string, body: T): Observable<U> {
  //   return this.http.post<U>(`${this.base_url}${url}`, body, {});
  // }

  public post<T, U>(url: string, body: T) {
    this.loading = true
    return this.http.post<U>(`${this.base_url}${url}`, body, {
      headers: {
        TOKEN: this.token ?? ''
      }
    });
  }

  // public put<T, U>(url: string, body: T): Observable<U> {
  //   return this.http.put<U>(`${this.base_url}${url}`, body, {});
  // }

  public put<T, U>(url: string, body: T) {
    return this.http.put<U>(`${this.base_url}${url}`, body, {
      headers: {
        TOKEN: this.token ?? ''
      }
    });
  }

  setLoggedInUserId(userId: string): void {
    this.loggedInUserId = userId;
    localStorage.setItem('loggedInUserId', userId);
  }

  getLoggedInUserId(): string | null {
    if (!this.loggedInUserId) {
      this.loggedInUserId = localStorage.getItem('loggedInUserId');
    }
    return this.loggedInUserId;
  }

  clearLoggedInUserId(): void {
    this.loggedInUserId = null;
    localStorage.removeItem('loggedInUserId');
  }

  setUsername(username: string): void {
    localStorage.setItem('username', username);
  }

  getUsername(): string | null {
    return localStorage.getItem('username');
  }

  clearUsername(): void {
    localStorage.removeItem('username');
  }

}


@Injectable({
  providedIn: 'root'
})

export class AuthGuard implements CanActivate {

  constructor(private router: Router) { }

  canActivate(): boolean {
    const token = localStorage.getItem('token');
    if (token) {
      return true; // Allow access
    } else {
      this.router.navigate(['/login']); // Redirect to login
      return false; // Deny access
    }
  }
}
