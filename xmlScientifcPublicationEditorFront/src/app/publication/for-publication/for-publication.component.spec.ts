import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ForPublicationComponent } from './for-publication.component';

describe('ForPublicationComponent', () => {
  let component: ForPublicationComponent;
  let fixture: ComponentFixture<ForPublicationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ForPublicationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ForPublicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
