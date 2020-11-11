// @ts-nocheck
import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { HistoricoPrecoCombustivel } from '../shared/model/historico-preco-combustivel.model';
import { PageEvent } from '@angular/material/paginator';

@Component({
  selector: 'app-historico-preco-combustivel-list',
  template: './historico-preco-combustivel-list.component.html'
  /*,
  styleUrls: ['./historico-preco-combustivel-list.component.css']*/
})


export class HistorioPrecoCombustivelComponent implements OnInit {

  //displayedColumns: string[] = [ 'dataColeta', 'valorCompra', 'valorVenda', 'revenda', 'bandeira', 'produto'];
  totalElements: number = 0;
  todos: HistoricoPrecoCombustivel[] = [];
  loading: boolean;
  //dataSource: HistoricoPrecoCombustivel[];
  //isLoadingResults = false;

  constructor(private _api: ApiService) { }

  ngOnInit() {
    this.getTodos({ page: "0", size: "10" });
  }

  private getTodos(request) {
    this.loading = true;
    this._api.getHistoricoPrecoCombustivel(request)
      .subscribe(data => {
        this.todos = data['content'];
        this.totalElements = data['totalElements'];
        this.loading = false;
      }, error => {
        this.loading = false;
      });
  }
 
  nextPage(event: PageEvent) {
    const request = {};
    request['page'] = event.pageIndex.toString();
    request['size'] = event.pageSize.toString();
    this.getTodos(request);
  }
 


}
