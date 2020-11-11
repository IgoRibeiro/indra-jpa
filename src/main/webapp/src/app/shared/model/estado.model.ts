export interface IEstado {
  id?: number;
  sigla?: string;
}

export class Estado implements IEstado {
  constructor(public id?: number, public sigla?: string) {}
}
