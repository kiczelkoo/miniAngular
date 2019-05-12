package com.craftsmanship.miniangular.web.rest;

import java.util.List;
import java.util.stream.Collectors;

import com.craftsmanship.miniangular.logic.DataProvider;
import com.craftsmanship.miniangular.model.IngredientModel;
import com.craftsmanship.miniangular.model.RecipeModel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchRestFacade {

    final private DataProvider dataProvider;

    public SearchRestFacade(final DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @GetMapping("/api/recipes-search")
    public List<RecipeModel> getRecipesByProducts(@RequestParam(name = "product") final List<String> products) {
        return this.dataProvider.getRecipes()
                .stream()
                .filter(recipe -> recipe.getIngredients().stream().map(
                        IngredientModel::getProductName).anyMatch(products::contains))
                .collect(Collectors.toList());
    }

}
