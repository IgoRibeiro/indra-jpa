export interface IUnidadeMedida {
  id?: number;
  nome?: string;
}

export class UnidadeMedida implements IUnidadeMedida {
  constructor(public id?: number, public nome?: string) {}
}
