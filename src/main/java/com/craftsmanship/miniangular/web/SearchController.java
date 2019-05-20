package com.craftsmanship.miniangular.web;

import com.craftsmanship.miniangular.logic.DataProvider;
import com.craftsmanship.miniangular.logic.ResourcesCustomHandler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class SearchController {

    final private DataProvider dataProvider;
    final private ResourcesCustomHandler resourcesCustomHandler;

    public SearchController(
            final DataProvider dataProvider,
            final ResourcesCustomHandler resourcesCustomHandler) {
        this.dataProvider = dataProvider;
        this.resourcesCustomHandler = resourcesCustomHandler;
    }

    @GetMapping("/recipes")
    public ModelAndView getRecipes(@RequestParam(name = "product") final List<String> products) throws IOException {
        return new ModelAndView("view/search-results")
                .addObject("selectedProducts", products)
                .addObject("products", this.dataProvider.getProducts())
                .addObject("resources", this.resourcesCustomHandler.readResources());

    }

}
