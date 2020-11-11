export interface IBandeira {
  id?: number;
  nome?: string;
}

export class Bandeira implements IBandeira {
  constructor(public id?: number, public nome?: string) {}
}
