import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  static readonly RECIPES_SEARCH_URL = '/api/recipes-search';
  static readonly RECIPES_DETAILS_URL = '/api/recipe-details';

  constructor(private http: HttpClient) {
  }

  getRecipesForProducts(products: string[]): Observable<RecipeModel[]> {
    let httpParams = new HttpParams();
    products.forEach(product => {
      httpParams = httpParams.append('product', product);
    });
    return this.http.get<RecipeModel[]>(SearchService.RECIPES_SEARCH_URL, {params: httpParams});
    // TODO do some error handling
  }

  getRecipeDetails(id: number): Observable<RecipeModel> {
    let httpParams = new HttpParams();
    httpParams = httpParams.append('id', id.toString());
    return this.http.get<RecipeModel>(SearchService.RECIPES_DETAILS_URL, {params: httpParams});
    // TODO do some error handling
  }
}

export interface RecipeModel {
  id: number;
  name: string;
  description: string;
  ingredients: IngredientModel[];
  category: string;
}

export interface IngredientModel {
  productName: string;
  amount: string;
}
