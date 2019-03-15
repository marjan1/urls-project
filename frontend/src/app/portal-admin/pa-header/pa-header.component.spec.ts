import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {PaHeaderComponent} from './pa-header.component';

describe('PaHeaderComponent', () => {
  let component: PaHeaderComponent;
  let fixture: ComponentFixture<PaHeaderComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaHeaderComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
