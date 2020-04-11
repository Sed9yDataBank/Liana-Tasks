import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {DragDropModule} from '@angular/cdk/drag-drop';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { HomeComponent } from './home/home.component';
import { GlobalService } from './apiConnect/services/global.service';
import { HttpClientModule } from '@angular/common/http';
import { httpInterceptorProviders } from './apiConnect/authentication/auth-interceptor';
import { BoardsComponent } from './boards/boards.component';
import { TasksComponent } from './tasks/tasks.component';
import { DragDirective } from './boards/drag.directive';
import { LayoutModule } from '@angular/cdk/layout';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    HomeComponent,
    BoardsComponent,
    TasksComponent,
    DragDirective
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    DragDropModule,
    LayoutModule
  ],
  exports: [FormsModule,
  ],
  providers: [GlobalService, httpInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
