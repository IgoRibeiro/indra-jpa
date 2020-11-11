import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders, HttpErrorResponse, HttpParams } from '@angular/common/http';
import { catchError, tap, map } from 'rxjs/operators';
import { HistoricoPrecoCombustivel } from '../app/shared/model/historico-preco-combustivel.model';
import { environment } from '../environments/environment';
import { createRequestOption } from '../app/shared/util/request-util';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};


@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) { }

  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      console.error(error);

      return of(result as T);
    };
  }

  getHistoricoPrecoCombustivel (req?: any): Observable<HistoricoPrecoCombustivel[]> {
    const options = createRequestOption(req);

    return this.http.get<HistoricoPrecoCombustivel[]>(environment.apiUrl + '/historico-preco-combustivel', {params: options})
      .pipe(
        tap(historicos => console.log('leu os históricos')),
        catchError(this.handleError('getHistoricoPrecoCombustivel', []))
      );
  }


}

