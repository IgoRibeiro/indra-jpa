import { IMunicipio } from '../model/municipio.model';

export interface IRevenda {
  id?: number;
  nome?: string;
  municipio?: IMunicipio;
}

export class Revenda implements IRevenda {
  constructor(public id?: number, public nome?: string, public municipio?: IMunicipio) {}
}
