package com.craftsmanship.miniangular.web;

import com.craftsmanship.miniangular.logic.DataProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainPageController {

    final private DataProvider dataProvider;

    public MainPageController(final DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @GetMapping("/")
    public ModelAndView mainPage() {
        return new ModelAndView("index").addObject("products", this.dataProvider.getProducts());
    }

}
