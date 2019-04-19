package com.craftsmanship.miniangular.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecipeModel {


    private Long id;
    private String name;
    private String description;
    private List<IngredientModel> ingredients;
    private String category;

}
