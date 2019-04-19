package com.craftsmanship.miniangular.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IngredientModel {

    private String productName;
    private String amount;
}
