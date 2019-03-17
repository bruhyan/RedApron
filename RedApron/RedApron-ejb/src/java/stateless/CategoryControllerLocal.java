/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Category;
import entity.Recipe;
import exceptions.CategoryNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author MX
 */
@Local
public interface CategoryControllerLocal {

    public Category createNewCategory(Category category);

    public Category retrieveCategoryById(long id) throws CategoryNotFoundException;

    public Category retrieveCategoryByName(String name) throws CategoryNotFoundException;

    public List<Category> retrieveAllStaffs();

    public List<Recipe> retrieveCategoryRecipesById(Long id) throws CategoryNotFoundException;

    public List<Recipe> retrieveCategoryRecipesByName(String name) throws CategoryNotFoundException;

    public List<Recipe> retrieveCategorySubscPlansById(Long id) throws CategoryNotFoundException;

    public List<Recipe> retrieveCategorySubscPlansByName(String name) throws CategoryNotFoundException;

    public void deleteCategory(Long id) throws CategoryNotFoundException;
    
}
