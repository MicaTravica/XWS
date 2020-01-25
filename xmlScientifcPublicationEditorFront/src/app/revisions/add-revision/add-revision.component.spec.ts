import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddRevisionComponent } from './add-revision.component';

describe('AddRevisionComponent', () => {
  let component: AddRevisionComponent;
  let fixture: ComponentFixture<AddRevisionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddRevisionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddRevisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
