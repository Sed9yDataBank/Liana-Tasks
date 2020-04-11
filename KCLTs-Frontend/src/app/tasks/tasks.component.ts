import { Component, OnInit } from '@angular/core';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { Board } from 'src/app/tasks/Board';
import { Column } from 'src/app/tasks/Column';
import { GlobalService } from '../apiConnect/services/global.service';
import { Router, ActivatedRoute } from '@angular/router';
import { BoardService } from '../apiConnect/services/board.service';
import { Task } from '../apiConnect/models/Task';
import { TaskService } from '../apiConnect/services/task.service';
import { NewTask } from '../apiConnect/models/NewTask';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {

  showDiv: boolean;
  passcode: string;
  taskList: Task[];
  list = [];
  descDialogVisible = false;
  dialogDescData = '';
  newTaskModel = new NewTask();

  constructor(private service: TaskService, private boardService: BoardService, private global: GlobalService, private router: Router, private route: ActivatedRoute) { }

  board: Board = new Board('Test Board', [
    new Column('Ideas', [
      "Some random idea",
      "This is another random idea",
      "build an awesome application"
    ]),
    new Column('Research', [
      "Lorem ipsum",
      "foo",
      "This was in 'Research' column"
    ]),
    new Column('Todo', [
      'Get to work',
      'Pick up groceries',
      'Go home',
      'Fall asleep',
      'Add drag and drop image',
      'Add invite pop up',
      'Add status and deadline',
      'Add delete edit',
      'Add blog section',
      'Connect rest of apis'

    ]),
    new Column('Done', [
      'Get up',
      'Brush teeth',
      'Take a shower',
      'Check e-mail',
      'Walk dog'
    ])
  ]);

  ngOnInit(): void {
    if (! this.global.getLogged()) {
      this.router.navigate(['/login']);
    }


  }

  drop(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex);
    }
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



  onPublish() {
    let valid = false;
    if (this.newTaskModel.task.title.length > 0 
        && this.newTaskModel.task.status.length > ) {
          valid = true;
    }

    if (valid) {
      this.service.create(this.newTaskModel).subscribe( data => {
      
        },
        error => {
          console.error(error);
        });
    }
  }
  send() {
    this.boardService.enterPass(this.passcode).subscribe(data => {
      console.log("Sucess, User Invited");
    }, error => {
      console.log("Invalid Pass Code Please Try Again");
    });
  }

}
