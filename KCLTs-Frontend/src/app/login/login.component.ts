import { GlobalService } from 'src/app/apiConnect/services/global.service';
import { Component, OnInit, AfterViewInit } from '@angular/core';
import { AuthLoginInfo } from 'src/app/apiConnect/authentication/login-info';
import { AuthService } from 'src/app/apiConnect/authentication/auth.service';
import { TokenStorageService } from 'src/app/apiConnect/authentication/token-storage.service';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: any = {};
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  private loginInfo: AuthLoginInfo;

  constructor(private authService: AuthService, private tokenStorage: TokenStorageService,
    private router: Router, private route: ActivatedRoute, private global: GlobalService) { }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getAuthorities();
      this.router.navigate(['./u/boards']);
    }
  }

  onSubmit() {
    this.loginInfo = new AuthLoginInfo();
    this.loginInfo.username = this.form.username;
    this.loginInfo.password = this.form.password;
    this.authService.attemptAuth(this.loginInfo).subscribe(
      data => {
        this.tokenStorage.saveToken(data.accessToken);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveAuthorities(data.authorities);
        this.global.setUid(data.uid);
        this.global.setCurrentBoardId(data.pid);
        this.global.setLogged(true);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.roles = this.tokenStorage.getAuthorities();
        this.router.navigate(['./u/boards']);
      },
      error => {
        console.log(error);
        this.errorMessage = error.error.message;
        this.isLoginFailed = true;
      }
    );
  }

  reloadPage() {
    window.location.reload();
  }
}
