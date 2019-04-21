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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author MX
 */
@Stateless
@Local(RecipeControllerLocal.class)
public class RecipeController implements RecipeControllerLocal {

    @PersistenceContext(unitName = "RedApron-ejbPU")
    private EntityManager em;
    
    @Override
    public Recipe createNewRecipe(Recipe recipe){
        em.persist(recipe);
        em.flush();
        
        return recipe;
    }
    
    @Override
    public Recipe retrieveRecipeById(Long id) throws RecipeNotFoundException{
        Recipe recipe = em.find(Recipe.class, id);
        
        if(recipe != null){
            return recipe;
        }else{
            throw new RecipeNotFoundException("Recipe does not exist!");
        }
    }
    
    @Override
    public Recipe retrieveRecipeByName(String name) throws RecipeNotFoundException
    {
        Query query = em.createQuery("SELECT c FROM Recipe c WHERE c.name = :inName");
        query.setParameter("inName", name);
        
        try
        {
            return (Recipe) query.getSingleResult();
        }
        catch (NoResultException | NonUniqueResultException ex)
        {
            throw new RecipeNotFoundException("Recipe with name "+ name + " does not exist!");
        }
    }
    
    @Override
    public List<Recipe> retrieveAllRecipes()
    {
        Query query = em.createQuery("SELECT c FROM Recipe c");
        
        return query.getResultList();
    }
    
    @Override
    public List<Category> retriveCategoryByRecipeId(Long id) throws RecipeNotFoundException{
        Recipe recipe = retrieveRecipeById(id);
        
        recipe.getCategories().size();
        
        return recipe.getCategories();
    }
    
    
    @Override
    public List<Step> retrieveOrderedStepsByRecipeId(Long id) throws RecipeNotFoundException{
                        Query query = em.createQuery("SELECT s FROM Step s, Recipe r WHERE r.recipeId=:id AND s MEMBER OF r.steps ORDER BY s.orderNum ASC");

        query.setParameter("id", id);

        return query.getResultList();
    }
    
    @Override
    public List<Category> retriveCategoryByRecipeName(String name) throws RecipeNotFoundException{
        Recipe recipe = retrieveRecipeByName(name);
        
        Query query = em.createQuery("SELECT r FROM Category r WHERE r.recipes.name:name");
        query.setParameter("name", name);
        
        return query.getResultList();
    }
    
    @Override
    public void deleteRecipe(Long id) throws RecipeNotFoundException{
        Recipe recipe = retrieveRecipeById(id);
        em.remove(recipe);
    }
    
    @Override
    public void updateRecipe(Recipe recipe) throws RecipeNotFoundException {

        Recipe recipeToUpdate = retrieveRecipeById(recipe.getRecipeId());
        recipeToUpdate.setIngredients(recipe.getIngredients());
        recipeToUpdate.setShortDescription(recipe.getShortDescription());
        recipeToUpdate.setCategories(recipe.getCategories());
        recipeToUpdate.setSteps(recipe.getSteps());
        recipeToUpdate.setImage(recipe.getImage());
        recipeToUpdate.setIsAvailable(recipe.getIsAvailable());
        recipeToUpdate.setName(recipe.getName());

    }
}
