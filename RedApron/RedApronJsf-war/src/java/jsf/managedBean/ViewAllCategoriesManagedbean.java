/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Category;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import stateless.CategoryControllerLocal;

/**
 *
 * @author mdk12
 */
@Named(value = "viewAllCategoriesManagedbean")
@RequestScoped
public class ViewAllCategoriesManagedbean {

    @EJB(name = "CategoryControllerLocal")
    private CategoryControllerLocal categoryControllerLocal;

    /**
     * Creates a new instance of ViewAllCategoriesManagedbean
     */
    private List<Category> categories;

    public ViewAllCategoriesManagedbean() {
        this.categories = new ArrayList<>();
    }
    
    @PostConstruct
    public void postConstruct(){
        this.categories = categoryControllerLocal.retrieveAllCategories();
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
    
    

}
