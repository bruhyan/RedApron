/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Category;
import entity.Recipe;
import exceptions.CategoryNotFoundException;
import exceptions.RecipeNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import org.primefaces.model.DualListModel;
import stateless.CategoryControllerLocal;
import stateless.RecipeControllerLocal;

/**
 *
 * @author mdk12
 */
@Named(value = "categoryManagementManagedBean")
@ViewScoped
public class categoryManagementManagedBean implements Serializable {

    @EJB(name = "CategoryControllerLocal")
    private CategoryControllerLocal categoryControllerLocal;

    @EJB(name = "RecipeControllerLocal")
    private RecipeControllerLocal recipeControllerLocal;

    /**
     * Creates a new instance of categoryManagementManagedBean
     */
    private List<Category> categories;
    private List<Category> filteredCategoryEntities;
    private Category selectedCategoryToView;
    private Category newCategory;
    private Category selectedCategoryToUpdate;
    private List<Recipe> recipesToUpdate;
    private List<Recipe> newRecipes;
    private DualListModel<Recipe> newRecipeChoices;
    private DualListModel<Recipe> updateRecipeChoices;
    private List<Recipe> recipes;
    private Long categoryIdNew;

    public categoryManagementManagedBean() {
        this.newCategory = new Category();
    }

    @PostConstruct
    public void postConstruct() {
        this.categories = categoryControllerLocal.retrieveAllCategories();

        this.recipes = recipeControllerLocal.retrieveAllRecipes();
        this.newRecipes = new ArrayList<>();

        newRecipeChoices = new DualListModel<Recipe>(recipes, newRecipes);

    }

    public List<Category> getCategories() {
        return categories;
    }

    public void createNewCategory(ActionEvent event) {

        try {
            Category category = categoryControllerLocal.createNewCategory(newCategory);

            for (Recipe recipe : newRecipeChoices.getTarget()) {
                Recipe temp = recipeControllerLocal.retrieveRecipeById(recipe.getRecipeId());
                temp.getCategories().add(category);
                category.getRecipes().add(temp);
            }

            categories.add(category);
            this.newRecipes = new ArrayList<>();

            newRecipeChoices = new DualListModel<Recipe>(recipes, newRecipes);
            newCategory = new Category();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Category created successfully (Product ID: " + category.getCategoryId() + ")", null));
        } catch (RecipeNotFoundException ex) {
            System.out.println("Recipe not found!");
        }
    }

    public void deleteCategory(ActionEvent event) {

        try {
            Category categoryToDelete = (Category) event.getComponent().getAttributes().get("categoryToDelete");
            categoryControllerLocal.deleteCategory(categoryToDelete.getCategoryId());

            categories.remove(categoryToDelete);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Category deleted successfully", null));
        } catch (CategoryNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void doUpdateCategory(ActionEvent event) {
        selectedCategoryToUpdate = (Category) event.getComponent().getAttributes().get("categoryToUpdate");
    }

    public void updateCategory(ActionEvent event) {

        try {
            categoryControllerLocal.updateCategory(selectedCategoryToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Category updated successfully", null));
        } catch (CategoryNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getFilteredCategoryEntities() {
        return filteredCategoryEntities;
    }

    public void setFilteredCategorytEntities(List<Category> filteredCategoryEntities) {
        this.filteredCategoryEntities = filteredCategoryEntities;
    }

    public Category getSelectedCategoryToView() {
        return selectedCategoryToView;
    }

    public void setSelectedCategoryToView(Category selectedCategoryToView) {
        this.selectedCategoryToView = selectedCategoryToView;
    }

    public Category getNewCategory() {
        return newCategory;
    }

    public void setNewCategory(Category newCategory) {
        this.newCategory = newCategory;
    }

    public Category getSelectedCategoryToUpdate() {
        return selectedCategoryToUpdate;
    }

    public void setSelectedCategoryToUpdate(Category selectedCategoryToUpdate) {
        this.selectedCategoryToUpdate = selectedCategoryToUpdate;
    }

    public Long getCategoryIdNew() {
        return categoryIdNew;
    }

    public void setCategoryIdNew(Long categoryIdNew) {
        this.categoryIdNew = categoryIdNew;
    }

    public List<Recipe> getRecipesToUpdate() {
        return recipesToUpdate;
    }

    public void setRecipesToUpdate(List<Recipe> recipesToUpdate) {
        this.recipesToUpdate = recipesToUpdate;
    }

    public RecipeControllerLocal getRecipeControllerLocal() {
        return recipeControllerLocal;
    }

    public void setRecipeControllerLocal(RecipeControllerLocal recipeControllerLocal) {
        this.recipeControllerLocal = recipeControllerLocal;
    }

    public List<Recipe> getNewRecipes() {
        return newRecipes;
    }

    public void setNewRecipes(List<Recipe> newRecipes) {
        this.newRecipes = newRecipes;
    }

    public DualListModel<Recipe> getNewRecipeChoices() {
        return newRecipeChoices;
    }

    public void setNewRecipeChoices(DualListModel<Recipe> newRecipeChoices) {
        this.newRecipeChoices = newRecipeChoices;
    }

}
