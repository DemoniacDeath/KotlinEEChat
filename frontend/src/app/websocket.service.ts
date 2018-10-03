import { Injectable } from '@angular/core';
import {webSocket} from 'rxjs/webSocket';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  socket$: Subject<any>;

  constructor() {
    this.socket$ = webSocket('ws://localhost:8080/api/');
    this.socket$.subscribe(
      () => {},
      (err) => console.error(err)
    );
  }
}
