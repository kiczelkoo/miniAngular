
package com.craftsmanship.miniangular.logic;

import com.craftsmanship.miniangular.model.IngredientModel;
import com.craftsmanship.miniangular.model.ProductModel;
import com.craftsmanship.miniangular.model.RecipeModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DataProvider {
    private static final String DESCRIPTON_PLCEHOLDER = "SPOSÓB PRZYGOTOWANIA";
    private static final String INGREDIENTS_SEPARATOR = ":";
    private static final String TITLE_SEPARATOR = "—";

    private Set<ProductModel> products = new HashSet<>();

    private Set<RecipeModel> recipes = new HashSet<>();

    @Value("classpath:data/products.txt")
    private Resource productsFile;

    @Value("classpath:data/recipes")
    private Resource recipesFiles;

    @PostConstruct
    public void readData() throws IOException {
        readProducts();
        readRecipes();
    }

    private void readProducts() throws IOException {
        this.productsFile.getFile();
        final BufferedReader br = new BufferedReader(new FileReader(this.productsFile.getFile()));
        String line;

        while ((line = br.readLine()) != null) {
            final String[] product = line.split(",");
            products.add(new ProductModel(product[0], product[1]));
        }
    }

    private void readRecipes() throws IOException {
        File[] listOfFiles = this.recipesFiles.getFile().listFiles();
        long count = 0;
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    List<String> recipeFile = new ArrayList<>();
                    while ((line = br.readLine()) != null) {
                        recipeFile.add(line);
                    }
                    recipes.add(createRecipe(recipeFile, count));
                }
            }
            count++;
        }

    }

    private RecipeModel createRecipe(final List<String> recipeFile, final long id) {
        return RecipeModel.builder().id(id).category(recipeFile.get(0)).name(readTitle(recipeFile.get(1)))
                .ingredients(createIngredients(recipeFile.subList(2, recipeFile.indexOf(DESCRIPTON_PLCEHOLDER))))
                .description(createDescription(
                        recipeFile.subList(recipeFile.indexOf(DESCRIPTON_PLCEHOLDER) + 1, recipeFile.size())))
                .build();
    }

    private String createDescription(final List<String> description) {
        return String.join("\n", description);
    }

    private List<IngredientModel> createIngredients(final List<String> ingredientsList) {
        return ingredientsList.stream().map(line -> {
            final String[] elements = line.split(INGREDIENTS_SEPARATOR);
            return IngredientModel.builder().productName(elements[0]).amount(elements[1]).build();

        }).collect(Collectors.toList());
    }

    private String readTitle(final String fullTitle) {
        return fullTitle.contains(TITLE_SEPARATOR) ? fullTitle.substring(0, fullTitle.indexOf("—")).trim() : fullTitle;
    }

    public Set<RecipeModel> getRecipes() {
        return recipes;
    }

    public Set<ProductModel> getProducts() {
        return products;
    }
}
