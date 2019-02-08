import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {ApprenticeComponent} from './apprentice.component';

describe('ApprenticeComponent', () => {
  let component: ApprenticeComponent;
  let fixture: ComponentFixture<ApprenticeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApprenticeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApprenticeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
