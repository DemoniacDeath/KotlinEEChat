import {Component, OnInit} from '@angular/core';
import {WebsocketService} from './websocket.service';
import {Subject} from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  input: string;
  messages: string[] = [];
  socket$: Subject<any>;

  constructor(private wsService: WebsocketService) {
    this.socket$ = wsService.socket$;
  }

  ngOnInit(): void {
    this.socket$.subscribe((msg: string) => {this.messages.push(msg);});
  }

  submit(): void {
    this.socket$.next(this.input);
    this.input = "";
  }

}
