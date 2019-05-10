
package com.craftsmanship.miniangular.logic;

import com.craftsmanship.miniangular.model.IngredientModel;
import com.craftsmanship.miniangular.model.ProductModel;
import com.craftsmanship.miniangular.model.RecipeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DataProvider {

    private static final String DESCRIPTON_PLCEHOLDER = "SPOSÓB PRZYGOTOWANIA";
    private static final String INGREDIENTS_SEPARATOR = ":";
    private static final String TITLE_SEPARATOR = "—";

    private Set<ProductModel> products = new HashSet<>();

    private Set<RecipeModel> recipes = new HashSet<>();

    @PostConstruct
    public void readData() throws IOException {
        readProducts();
        readRecipes();
    }

    private void readProducts() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream("data/products.txt")) {
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = br.readLine()) != null) {
                final String[] product = line.split(",");
                products.add(new ProductModel(product[0], product[1]));
            }
            br.close();
        }
    }

    private void readRecipes() throws IOException {
        PathMatchingResourcePatternResolver scanner = new PathMatchingResourcePatternResolver();
        Resource[] resources = scanner.getResources("data/recipes/*");
        if (resources == null || resources.length == 0) {
            log.warn("Warning: could not find any resources in this scanned package");
        } else {
            long count = 0;
            for (Resource resource : resources) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
                    String line;
                    List<String> recipeFile = new ArrayList<>();
                    while ((line = br.readLine()) != null) {
                        recipeFile.add(line);
                    }
                    recipes.add(createRecipe(recipeFile, count));
                }
                count++;
            }
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
