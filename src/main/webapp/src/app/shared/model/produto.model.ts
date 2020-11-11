import { IUnidadeMedida } from '../model/unidade-medida.model';

export interface IProduto {
  id?: number;
  nome?: string;
  unidadeMedida?: IUnidadeMedida;
}

export class Produto implements IProduto {
  constructor(public id?: number, public nome?: string, public unidadeMedida?: IUnidadeMedida) {}
}
