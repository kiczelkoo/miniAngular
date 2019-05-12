import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {MiniAngularBridge} from "../app.component";
import {RecipeModel, SearchService} from "../search.service";
import {takeUntil} from 'rxjs/operators';
import {Subject} from 'rxjs';

@Component({
  selector: 'mini-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.scss']
})
export class SearchResultsComponent implements OnInit, OnDestroy {

  @Input()
  selectedProducts: MiniAngularBridge;

  recipesModel: RecipeModel[];

  private unsubscribe: Subject<RecipeModel[]> = new Subject();

  constructor(private searchService: SearchService) {
  }

  ngOnInit() {
    this.searchService.getRecipesForProducts(this.selectedProducts.names)
      .pipe(takeUntil(this.unsubscribe))
      .subscribe(recipesModel => {
      this.recipesModel = recipesModel;
    });
  }

  ngOnDestroy(): void {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }

}
