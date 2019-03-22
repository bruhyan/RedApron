/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Recipe;
import exceptions.RecipeNotFoundException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import stateless.RecipeControllerLocal;

/**
 *
 * @author mdk12
 */
@FacesConverter(value = "pickListConverter")

public class PickListConverter implements Converter, Serializable {

    RecipeControllerLocal recipeController = lookupRecipeControllerLocal();

    /**
     * Creates a new instance of PickListConverter
     */

    public PickListConverter() {
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            System.out.println("HELLLLO");
            long recipeId = Long.parseLong(value);
            System.out.println(recipeId);

            Recipe recipe = lookupRecipeControllerLocal().retrieveRecipeById(recipeId);
            

            return recipe;
        }catch(RecipeNotFoundException ex){
            System.out.println("No Such recipe found!");
        }
        return null;
    }

    private RecipeControllerLocal lookupRecipeControllerLocal() {
        try {
            Context c = new InitialContext();
            return (RecipeControllerLocal) c.lookup("java:global/RedApron/RedApron-ejb/RecipeController!stateless.RecipeControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
