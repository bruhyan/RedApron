/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Recipe;
import java.util.List;

/**
 *
 * @author MX
 */
public class RetrieveAllRecipesRsp {
    private List <Recipe> recipeEntities;

    public RetrieveAllRecipesRsp() {
    }

    public RetrieveAllRecipesRsp(List<Recipe> recipeEntities) {
        this.recipeEntities = recipeEntities;
    }

    /**
     * @return the recipeEntities
     */
    public List <Recipe> getRecipeEntities() {
        return recipeEntities;
    }

    /**
     * @param recipeEntities the recipeEntities to set
     */
    public void setRecipeEntities(List <Recipe> recipeEntities) {
        this.recipeEntities = recipeEntities;
    }
    
    
}
