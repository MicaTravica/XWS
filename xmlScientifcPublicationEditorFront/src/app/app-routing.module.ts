import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { LoginComponent } from './core/login/login.component';
import { RegisterComponent } from './core/register/register.component';
import { LoginGuard } from './guards/login.service';
import { RoleGuard } from './guards/role.service';
import { ProfilDetailsComponent } from './profil/profil-details/profil-details.component';
import { SearchPublicationsComponent } from './publication/search-publications/search-publications.component';
import { AddPublicationComponent } from './publication/add-publication/add-publication.component';
import { MyPublicationsComponent } from './publication/my-publications/my-publications.component';
import { ForRevisionComponent } from './revisions/for-revision/for-revision.component';
import { ForPublicationComponent } from './publication/for-publication/for-publication.component';
import { AddRevisionComponent } from './revisions/add-revision/add-revision.component';
import { ProcessPublicationComponent } from './publication/process-publication/process-publication.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
  { path: 'register', component: RegisterComponent },
  { path: 'publications', component: SearchPublicationsComponent },
  { path: 'add_publication', component: AddPublicationComponent },
  { path: 'my_publications', component: MyPublicationsComponent },
  { path: 'for_revision', component: ForRevisionComponent },
  { path: 'add_revision', component: AddRevisionComponent },
  { path: 'for_publication', component: ForPublicationComponent },
  { path: 'process/:id', component: ProcessPublicationComponent },
  { path: 'profile', component: ProfilDetailsComponent },
  { path: '', redirectTo: 'publications', pathMatch: 'full' },

];

@NgModule({
  imports: [
    RouterModule.forRoot(routes),
    FormsModule,
    ReactiveFormsModule
  ],
  exports: [
    RouterModule,
    FormsModule,
    ReactiveFormsModule
  ]
})

export class AppRoutingModule { }
