import { ISiglaRegiao } from '../model/sigla-regiao.model';
import { IEstado } from '../model/estado.model';

export interface IRegiao {
  id?: number;
  siglaRegiao?: ISiglaRegiao;
  estado?: IEstado;
}

export class Regiao implements IRegiao {
  constructor(public id?: number, public siglaRegiao?: ISiglaRegiao, public estado?: IEstado) {}
}
