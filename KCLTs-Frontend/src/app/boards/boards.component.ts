import { Component, OnInit } from '@angular/core';
import { GlobalService } from '../apiConnect/services/global.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FileHandle } from './drag.directive';
import { User } from '../apiConnect/models/User';
import { NewBoard } from '../apiConnect/models/NewBoard';
import { BoardService } from '../apiConnect/services/board.service';
import { UserService } from '../apiConnect/services/user.service';
import { BoardList } from '../apiConnect/models/BoardList';
import { Board } from '../apiConnect/models/Board';

@Component({
  selector: 'app-boards',
  templateUrl: './boards.component.html',
  styleUrls: ['./boards.component.css']
})
export class BoardsComponent implements OnInit {

  showDiv: boolean;

  sourceUser: User[];
  assignedUser: User[];
  newBoard = new NewBoard();
  isLinear = true;
  
  boardList = new BoardList();
  boards: Board[];
  constructor(private service: BoardService, private userService: UserService, private global: GlobalService, private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    if (! this.global.getLogged()) {
      this.router.navigate(['/login']);
    }

    this.global.setCurrentBoardId(0);
    this.userService.getAllUser().subscribe( res => this.sourceUser = res.userList);
    this.assignedUser = [];

    this.service.getAllByUser().subscribe( list => this.boardList = list);
  }

  show()
  {
    this.showDiv = true; // Show-Hide Modal Check
    
  }
  //Bootstrap Modal Close event
  hide()
  {
    this.showDiv = false;
  }
  clearForm(){
    (<HTMLFormElement>document.getElementById("title")).reset();
  }

  logout() {
    window.sessionStorage.clear();
    this.router.navigate(['/login']);
    this.global.setLogged(false);
  }

  files: FileHandle[] = [];

  filesDropped(files: FileHandle[]): void {
    this.files = files;
  }

  stepChange(ev) {
    if ( ev.selectedIndex) {
    }
  }

  onSubmit() {
    if ((this.newBoard.title.length > 2)) {
      this.newBoard.users = [];
      this.assignedUser.forEach(u => {this.newBoard.users.push(u.userId); });
      this.newBoard.admin = this.global.getUid();
      this.service.create(this.newBoard).subscribe();
    } else {
      if (!(this.newBoard.title.length > 2)) {
        console.error("Invalid Title ! ");
      }
    }
  }

  selectPrgm(p: Board) {
    this.global.setCurrentBoardId(p.boardId);
    this.global.setTitle(p.title);
    this.router.navigate(['./../../t', 'tasks'], {relativeTo: this.route });
  }
}
