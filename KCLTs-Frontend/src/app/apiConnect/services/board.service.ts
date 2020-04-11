import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { NewBoard } from '../models/NewBoard';
import { BoardList } from '../models/BoardList';
import { GlobalService } from './global.service';
import { CodeDTO } from '../models/CodeDTO';

@Injectable({
  providedIn: 'root'
})
export class BoardService {
  url = environment.baseURL;
  constructor(private http: HttpClient, private global: GlobalService) { }

  create(newBoard: NewBoard) {
    return this.http.post<any>(this.url + '/createBoard/' + this.global.getUid(), newBoard);
  }
  getAll() {
    return this.http.get<BoardList>(this.url + '/getAllBoards');
  }
  getAllByAdmin(id: number) {
    return this.http.get<BoardList>(this.url + '/getAllBoardByAdmin/' + id);
  }

  getAllByUser() {
    return this.http.get<BoardList>(this.url + '/getAllBoardByUser/' + this.global.getUid());
  }
  enterPass(code: string) {
    const dto = new CodeDTO();
    dto.code = code;
    dto.uid = this.global.getUid();
    return this.http.post<any>(this.url + '/enterPrg', dto);
  }


  updateBoard(board: NewBoard) {
    let body = JSON.stringify(board);
    console.log(body);
    this.http.put(this.apiUrl + '/' + board.boardId, body)
      .toPromise()
      .then(res => console.log(res.json()));
  }

  deleteBoardById(board: NewBoard) {
    this.http.delete(this.apiUrl + '/' +board.boardId)
      .toPromise()
      .then(res => console.log(res.json()));
  }
}

