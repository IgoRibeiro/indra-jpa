import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HistorioPrecoCombustivelComponent} from './historico-preco-combustivel-list.component';

describe('HistorioPrecoCombustivelComponent', () => {
  let component: HistorioPrecoCombustivelComponent;
  let fixture: ComponentFixture<HistorioPrecoCombustivelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HistorioPrecoCombustivelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HistorioPrecoCombustivelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
