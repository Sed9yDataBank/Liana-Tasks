import { Injectable } from '@angular/core';
import { Task } from '../models/Task';

@Injectable({
  providedIn: 'root'
})
export class GlobalService {

  constructor() { }

  private currentboardId: number;
  private token: string;
  private uid: number;
  private suid: number;
  private title: string;
  private logged = false;
  private selectedTask: Task;

  getToken(): string {
    this.token = sessionStorage.getItem('token');
    return this.token;
  }
  setToken(tokenValue: string) {
    this.token = tokenValue;
    sessionStorage.setItem('token', this.token);
  }
  getCurrentBoardId(): number {
    this.currentboardId = +sessionStorage.getItem('currentboardID');
    return this.currentboardId;
  }
  setCurrentBoardId(boardId: number) {
    this.currentboardId = boardId;
    sessionStorage.setItem('currentboardID', '' + this.currentboardId);
  }
  getUid(): number {
    this.uid = +sessionStorage.getItem('uid');
    return this.uid;
  }
  setUid(uid: number) {
    this.uid = uid;
    sessionStorage.setItem('uid', '' + this.uid);
  }
  getSUid(): number {
    this.suid = +sessionStorage.getItem('suid');
    return this.suid;
  }
  setSUid(suid: number) {
    this.suid = suid;
    sessionStorage.setItem('suid', '' + this.suid);
  }
  getTitle(): string {
    this.title = sessionStorage.getItem('title');
    return this.title;
  }
  setTitle(title: string) {
    this.title = title;
    sessionStorage.setItem('title', this.title);
  }
  setLogged(v: boolean) {
    if ( !v ) {
      sessionStorage.clear();
      sessionStorage.setItem('logged', 'false');    // again created "logged" sessionstorage item, bcoz it is needed to be checked
    } else {
      sessionStorage.setItem('logged', 'true');
    }
    this.logged = v;
  }
  getLogged(): boolean {
    if ( sessionStorage.getItem('logged') === 'true') { this.logged = true; }
    if ( sessionStorage.getItem('logged') === 'false') { this.logged = false; }
    return this.logged;
  }

  getSelectedTask(): Task {
    return this.selectedTask;
  }
  setSelectedTask(task: Task) {
    this.selectedTask = task;
  }
}