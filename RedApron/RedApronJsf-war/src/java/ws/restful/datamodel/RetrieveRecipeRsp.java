/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Recipe;

/**
 *
 * @author MX
 */
public class RetrieveRecipeRsp {
    
    private Recipe recipe;

    public RetrieveRecipeRsp() {
    }

    public RetrieveRecipeRsp(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * @return the recipe
     */
    public Recipe getRecipe() {
        return recipe;
    }

    /**
     * @param recipe the recipe to set
     */
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
    
    
}
