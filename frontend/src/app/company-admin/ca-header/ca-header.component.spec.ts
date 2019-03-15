import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {CaHeaderComponent} from './ca-header.component';

describe('CaHeaderComponent', () => {
  let component: CaHeaderComponent;
  let fixture: ComponentFixture<CaHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CaHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CaHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
