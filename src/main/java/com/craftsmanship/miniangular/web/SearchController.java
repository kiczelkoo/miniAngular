package com.craftsmanship.miniangular.web;

import com.craftsmanship.miniangular.logic.DataProvider;
import com.craftsmanship.miniangular.model.IngredientModel;
import com.craftsmanship.miniangular.model.RecipeModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class SearchController {

    final private DataProvider dataProvider;

    public SearchController(final DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @GetMapping("/recipes")
    public ModelAndView getRecipes(@RequestParam(name = "product") final List<String> products) {
        List<RecipeModel> recipeModels = this.dataProvider.getRecipes()
                .stream()
                .filter(recipe -> recipe.getIngredients().stream().map(
                        IngredientModel::getProductName).anyMatch(products::contains))
                .collect(Collectors.toList());
        return new ModelAndView("view/search-results")
                .addObject("recipes", recipeModels)
                .addObject("selectedProducts", products)
                .addObject("products", this.dataProvider.getProducts());

    }

}
