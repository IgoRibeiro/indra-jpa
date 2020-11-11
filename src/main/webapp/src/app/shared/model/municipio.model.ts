import { IRegiao } from '../model/regiao.model';

export interface IMunicipio {
  id?: number;
  nome?: string;
  regiao?: IRegiao;
}

export class Municipio implements IMunicipio {
  constructor(public id?: number, public nome?: string, public regiao?: IRegiao) {}
}
