import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { LoginComponent } from './core/login/login.component';
import { RegisterComponent } from './core/register/register.component';
import { LoginGuard } from './guards/login.service';
import { RoleGuard } from './guards/role.service';
import { SearchPublicationsComponent } from './publication/search-publications/search-publications.component';
import { AddPublicationComponent } from './publication/add-publication/add-publication.component';
import { MyPublicationsComponent } from './publication/my-publications/my-publications.component';
import { ForRevisionComponent } from './revisions/for-revision/for-revision.component';
import { ForPublicationComponent } from './publication/for-publication/for-publication.component';
import { AddRevisionComponent } from './revisions/add-revision/add-revision.component';
import { ProcessPublicationComponent } from './publication/process-publication/process-publication.component';
import { AddCoverLetterComponent } from './add-cover-letter/add-cover-letter.component';
import { ProfilEditComponent } from './profil/profil-edit/profil-edit.component';
import { AddReviewersComponent } from './revisions/add-reviewers/add-reviewers.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent, canActivate: [LoginGuard] },
  { path: 'register', component: RegisterComponent },
  { path: 'publications', component: SearchPublicationsComponent },
  {
    path: 'add_publication', component: AddPublicationComponent, canActivate: [RoleGuard],
    data: { expectedRoles: 'ROLE_REVIEWER|ROLE_REDACTOR|ROLE_USER' }
  },
  {
    path: 'my_publications', component: MyPublicationsComponent, canActivate: [RoleGuard],
    data: { expectedRoles: 'ROLE_REVIEWER|ROLE_REDACTOR|ROLE_USER' }
  },
  {
    path: 'add_cover_letter', component: AddCoverLetterComponent, canActivate: [RoleGuard],
    data: { expectedRoles: 'ROLE_REVIEWER|ROLE_REDACTOR|ROLE_USER' }
  },
  {
    path: 'profil', component: ProfilEditComponent, canActivate: [RoleGuard],
    data: { expectedRoles: 'ROLE_REVIEWER|ROLE_REDACTOR|ROLE_USER' }
  },
  {
    path: 'for_revision', component: ForRevisionComponent, canActivate: [RoleGuard],
    data: { expectedRoles: 'ROLE_REVIEWER|ROLE_REDACTOR' }
  },
  {
    path: 'add_revision', component: AddRevisionComponent, canActivate: [RoleGuard],
    data: { expectedRoles: 'ROLE_REVIEWER|ROLE_REDACTOR' }
  },
  { path: 'for_publication', component: ForPublicationComponent, canActivate: [RoleGuard], data: { expectedRoles: 'ROLE_REDACTOR' } },
  { path: 'process/:id', component: ProcessPublicationComponent, canActivate: [RoleGuard], data: { expectedRoles: 'ROLE_REDACTOR' } },
  { path: 'add_rev/:id', component: AddReviewersComponent, canActivate: [RoleGuard], data: { expectedRoles: 'ROLE_REDACTOR' } },
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
