import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddCoverLetterComponent } from './add-cover-letter.component';

describe('AddCoverLetterComponent', () => {
  let component: AddCoverLetterComponent;
  let fixture: ComponentFixture<AddCoverLetterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddCoverLetterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddCoverLetterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
