import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task } from '../../modals/task.model'; // Assuming your Task model is in the same folder

@Injectable({
  providedIn: 'root'
})
export class TaskServices {

  private apiUrl = 'http://localhost:8089/SaFeAgile/tasks';

  constructor(private http: HttpClient) { }

  getTaskById(id: string): Observable<Task> {
    return this.http.get<Task>(`${this.apiUrl}/${id}`);
  }

  updateTask(id: string, task: Task): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, task);
  }

  createTask(task: Task): Observable<Task> {
    // POST request to the /add endpoint
    return this.http.post<Task>(`${this.apiUrl}/add`, task);
  }

  deleteTask(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
  getAllTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(this.apiUrl);
  }
}
