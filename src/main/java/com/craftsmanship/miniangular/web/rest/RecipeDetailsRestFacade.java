package com.craftsmanship.miniangular.web.rest;

import java.util.Optional;

import com.craftsmanship.miniangular.logic.DataProvider;
import com.craftsmanship.miniangular.model.RecipeModel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecipeDetailsRestFacade {

    final private DataProvider dataProvider;

    public RecipeDetailsRestFacade(final DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @GetMapping("/api/recipe-details")
    public RecipeModel getRecipeDetailsById(@RequestParam(name = "id") final Long recipeId) {
        return this.dataProvider.getRecipes().stream().filter(r -> r.getId().equals(recipeId)).findFirst().get();
    }
}
