import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { SignupComponent } from './signup/signup.component';
import { BoardsComponent } from './boards/boards.component';
import { TasksComponent } from './tasks/tasks.component';


const routes: Routes = [
  {
    path: '', component: HomeComponent
  },
  {
    path: 'signup', component: SignupComponent
  },
  {
    path: 'boards', component: BoardsComponent
  },
  {
    path: 'tasks', component: TasksComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
