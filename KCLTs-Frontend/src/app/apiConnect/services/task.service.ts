import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { NewTask } from './../models/NewTask';
import { Task } from '../models/Task';
import { TaskList } from '../models/TaskList';
import { GlobalService } from './global.service';
import { SubTask } from '../models/SubTask';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  url = environment.baseURL;
  constructor(private http: HttpClient, private global: GlobalService) { }

  create(newTask: NewTask) {
    return this.http.post<any>(this.url + '/createTask', newTask);
  }

  getTaskById(id: number) {
    return this.http.get<Task>(this.url + '/getTaskById/' + id);
  }
  getallTaskByUser() {
    return this.http.get<TaskList>(this.url + '/getAllTaskByUser/' + this.global.getCurrentBoardId() + '/' + this.global.getUid());
  }

  editTask(task: Task) {
    return this.http
      .put(this.http + '/' + task.taskId, JSON.stringify(task))
      .toPromise();
  }
 
  deleteTaskById(task: Task) {
    return this.http.delete(this.http + '/' + task.taskId)
      .toPromise();

  }

  getSubTask(id: string) {
    return this.http.get(this.http + '/' + id + '/subTasks')
      .map(res => <SubTask[]>res.json().data);
  }
}