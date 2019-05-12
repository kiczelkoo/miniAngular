import {Component, OnInit} from '@angular/core';

declare var selectedProducts: MiniAngularBridge;

@Component({
  selector: 'mini-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit{
  title = 'miniAngularClient';

  selectedProducts: MiniAngularBridge;

  ngOnInit(): void {
    this.selectedProducts = selectedProducts;
  }

}

export interface MiniAngularBridge {
  names: string[];
}
