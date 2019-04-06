/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Category;
import exceptions.CategoryNotFoundException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import stateless.CategoryControllerLocal;
import stateless.RecipeControllerLocal;


@FacesConverter(value = "pickListConverterRecipe")

public class PickListConverterRecipe implements Converter, Serializable {

    CategoryControllerLocal categoryController = lookupCategoryControllerLocal();

    /**
     * Creates a new instance of PickListConverter
     */

    public PickListConverterRecipe() {
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return value.toString();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            System.out.println("YOOOOO");
            long categoryId = Long.parseLong(value);
            System.out.println(categoryId);

            Category category = lookupCategoryControllerLocal().retrieveCategoryById(categoryId);
            

            return category;
        } catch(CategoryNotFoundException ex){
            System.out.println("No Such category found!");
        }
        return null;
    }

    private CategoryControllerLocal lookupCategoryControllerLocal() {
        try {
            Context c = new InitialContext();
            return (CategoryControllerLocal) c.lookup("java:global/RedApron/RedApron-ejb/CategoryController!stateless.CategoryControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    
}
