import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProcessPublicationComponent } from './process-publication.component';

describe('ProcessPublicationComponent', () => {
  let component: ProcessPublicationComponent;
  let fixture: ComponentFixture<ProcessPublicationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProcessPublicationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcessPublicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
