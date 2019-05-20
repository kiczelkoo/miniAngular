package com.craftsmanship.miniangular.web;

import com.craftsmanship.miniangular.logic.DataProvider;
import com.craftsmanship.miniangular.logic.ResourcesCustomHandler;
import com.craftsmanship.miniangular.model.RecipeModel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Optional;

@Controller
public class RecipeDetailsController {

    final private DataProvider dataProvider;
    final ResourcesCustomHandler resourcesCustomHandler;

    public RecipeDetailsController(
            final DataProvider dataProvider,
            final ResourcesCustomHandler resourcesCustomHandler) {
        this.dataProvider = dataProvider;
        this.resourcesCustomHandler = resourcesCustomHandler;
    }

    @GetMapping("/recipe-details")
    public ModelAndView getRecipeDetails(@RequestParam(name = "id") final Long recipeId) throws IOException {
        final Optional<RecipeModel>
                recipe = this.dataProvider.getRecipes().stream().filter(r -> r.getId().equals(recipeId)).findFirst();
        if (recipe.isPresent()) {
            return new ModelAndView("view/recipe-details")
                    .addObject("recipe", recipe.get())
                    .addObject("recipeId", recipe.get().getId())
                    .addObject("resources", this.resourcesCustomHandler.readResources());
        } else {
            return new ModelAndView("view/recipe-details").addObject(
                    "error-message",
                    "No recipe with id " + recipeId + " found.");
        }
    }

}
