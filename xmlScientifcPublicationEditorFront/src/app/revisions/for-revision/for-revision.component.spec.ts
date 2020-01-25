import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ForRevisionComponent } from './for-revision.component';

describe('ForRevisionComponent', () => {
  let component: ForRevisionComponent;
  let fixture: ComponentFixture<ForRevisionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ForRevisionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ForRevisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
