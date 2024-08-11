import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UserStory } from '../modals/userstory.model';

@Injectable({
  providedIn: 'root'
})
export class UserstoryService {
  private apiUrl = 'http://localhost:8089/SaFeAgile/us';

  constructor(private http: HttpClient) { }

  getUserStories(): Observable<UserStory[]> {
    return this.http.get<UserStory[]>(this.apiUrl);
  }

  getUserStoryById(id: string): Observable<UserStory> {
    return this.http.get<UserStory>(`${this.apiUrl}/${id}`);
  }

  createUserStory(userStory: UserStory): Observable<UserStory> {
    return this.http.post<UserStory>(this.apiUrl, userStory);
  }

  updateUserStory(id: string, userStory: UserStory): Observable<UserStory> {
    return this.http.put<UserStory>(`${this.apiUrl}/${id}`, userStory);
  }

  deleteUserStory(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
