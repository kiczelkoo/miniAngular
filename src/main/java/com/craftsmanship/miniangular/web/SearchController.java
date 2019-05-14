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
        return new ModelAndView("view/search-results")
                .addObject("selectedProducts", products)
                .addObject("products", this.dataProvider.getProducts());

    }

}
