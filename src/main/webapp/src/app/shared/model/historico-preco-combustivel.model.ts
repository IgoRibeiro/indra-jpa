import { Moment } from 'moment';
import { IRevenda } from '../model/revenda.model';
import { IBandeira } from '../model/bandeira.model';
import { IProduto } from '../model/produto.model';

export interface IHistoricoPrecoCombustivel {
  id?: number;
  dataColeta?: Moment;
  valorCompra?: number;
  valorVenda?: number;
  revenda?: IRevenda;
  bandeira?: IBandeira;
  produto?: IProduto;
}

export class HistoricoPrecoCombustivel implements IHistoricoPrecoCombustivel {
  constructor(
    public id?: number,
    public dataColeta?: Moment,
    public valorCompra?: number,
    public valorVenda?: number,
    public revenda?: IRevenda,
    public bandeira?: IBandeira,
    public produto?: IProduto
  ) {}
}
