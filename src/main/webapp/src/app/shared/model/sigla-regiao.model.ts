export interface ISiglaRegiao {
  id?: number;
  sigla?: string;
}

export class SiglaRegiao implements ISiglaRegiao {
  constructor(public id?: number, public sigla?: string) {}
}
