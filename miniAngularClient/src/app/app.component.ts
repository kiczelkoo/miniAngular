import {Component, OnInit} from '@angular/core';

declare var miniAngularBridge: MiniAngularBridge;

@Component({
  selector: 'mini-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'miniAngularClient';

  miniAngularBridge: MiniAngularBridge;

  ngOnInit(): void {
    this.miniAngularBridge = miniAngularBridge;
  }

}

export interface MiniAngularBridge {
  pageName: string;
  products: string[];
  recipeId: number;
}

