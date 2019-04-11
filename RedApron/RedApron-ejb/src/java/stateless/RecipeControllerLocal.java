/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Category;
import entity.Recipe;
import entity.Step;
import exceptions.RecipeNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author MX
 */
@Local
public interface RecipeControllerLocal {

    public Recipe createNewRecipe(Recipe recipe);

    public Recipe retrieveRecipeById(Long id) throws RecipeNotFoundException;

    public Recipe retrieveRecipeByName(String name) throws RecipeNotFoundException;

    public List<Recipe> retrieveAllRecipes();

    public List<Category> retriveCategoryByRecipeId(Long id) throws RecipeNotFoundException;

    public List<Category> retriveCategoryByRecipeName(String name) throws RecipeNotFoundException;

    public void deleteRecipe(Long id) throws RecipeNotFoundException;

    public void updateRecipe(Recipe recipe) throws RecipeNotFoundException;

    public List<Step> retrieveOrderedStepsByRecipeId(Long id) throws RecipeNotFoundException;
    
}
