import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { NewSubTask } from './../models/NewSubTask';
import { SubTask } from '../models/SubTask';
import { SubTaskList } from '../models/SubTaskList';
import { GlobalService } from './global.service';
import { TaskService } from './task.service';

@Injectable({
  providedIn: 'root'
})
export class SubTaskService {
  url = environment.baseURL;
  constructor(private http: HttpClient, private global: GlobalService, private task: TaskService) { }

  create(newSubTask: NewSubTask) {
    return this.http.post<any>(this.url + '/createSubTask', newSubTask);
  }

  getTaskById(id: number) {
    return this.http.get<SubTask>(this.url + '/getSubTaskById/' + id);
  }
  getallSubTaskByTaskId(id: number) {
    return this.http.get<SubTaskList>(this.url + '/getAllSubTaskByTask/' + this.global.getCurrentBoardId() + '/' + this.task.getTaskById(id));
  }
 
  editSubTask(subtask: SubTask) {
    return this.http.put(this.http + '/' + subtask.subTaskId, JSON.stringify(subtask))
      .toPromise();
  }

  deleteSubTask(subtask: SubTask) {
    return this.http.delete(this.http + '/' + subtask.subTaskId)
      .toPromise();
  }
}