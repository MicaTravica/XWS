import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';

import { HeaderComponent } from './core/header/header.component';
import { MenuComponent } from './core/menu/menu.component';
import { LoginComponent } from './core/login/login.component';
import { RegisterComponent } from './core/register/register.component';

import { UserService } from './services/user-service/user.service';
import { LoginGuard } from './guards/login.service';
import { RoleGuard } from './guards/role.service';
import { AuthService } from './services/auth-service/auth.service';
import { ProfilDetailsComponent } from './profil/profil-details/profil-details.component';
import { ProfilEditComponent } from './profil/profil-edit/profil-edit.component';
import { SearchPublicationsComponent } from './publication/search-publications/search-publications.component';
import { MyPublicationsComponent } from './publication/my-publications/my-publications.component';
import { AddPublicationComponent } from './publication/add-publication/add-publication.component';
import { ForPublicationComponent } from './publication/for-publication/for-publication.component';
import { AddRevisionComponent } from './revisions/add-revision/add-revision.component';
import { ForRevisionComponent } from './revisions/for-revision/for-revision.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MenuComponent,
    LoginComponent,
    RegisterComponent,
    ProfilDetailsComponent,
    ProfilEditComponent,
    SearchPublicationsComponent,
    MyPublicationsComponent,
    AddPublicationComponent,
    ForRevisionComponent,
    ForPublicationComponent,
    AddRevisionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule
  ],
  providers: [
    UserService,
    AuthService,
    LoginGuard,
    RoleGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
