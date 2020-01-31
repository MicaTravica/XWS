import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SeeRevisionComponent } from './see-revision.component';

describe('SeeRevisionComponent', () => {
  let component: SeeRevisionComponent;
  let fixture: ComponentFixture<SeeRevisionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeeRevisionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SeeRevisionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
