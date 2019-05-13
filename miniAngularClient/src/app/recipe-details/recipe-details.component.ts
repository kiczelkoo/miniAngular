import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Subject} from "rxjs";
import {RecipeModel, SearchService} from "../search.service";
import {MiniAngularBridge} from "../app.component";
import {takeUntil} from "rxjs/operators";

@Component({
  selector: 'mini-recipe-details',
  templateUrl: './recipe-details.component.html',
  styleUrls: ['./recipe-details.component.scss']
})
export class RecipeDetailsComponent implements OnInit, OnDestroy {

  @Input()
  recipeId: number;

  constructor(private searchService: SearchService) { }

  recipeModel: RecipeModel;

  private unsubscribe: Subject<RecipeModel[]> = new Subject();

  ngOnInit() {
    this.searchService.getRecipeDetails(this.recipeId)
      .pipe(takeUntil(this.unsubscribe))
      .subscribe(recipeModel => {
        this.recipeModel = recipeModel;
      });
  }

  ngOnDestroy(): void {
    this.unsubscribe.next();
    this.unsubscribe.complete();
  }

}
