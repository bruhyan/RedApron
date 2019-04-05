/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Category;
import entity.Recipe;
import entity.Step;
import exceptions.CategoryNotFoundException;
import exceptions.RecipeNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.FlowEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DualListModel;
import stateless.CategoryControllerLocal;
import stateless.RecipeControllerLocal;
import stateless.StepControllerLocal;


@Named(value = "recipeManagementManagedBean")
@ViewScoped
public class RecipeManagementManagedBean implements Serializable {

    @EJB(name = "StepControllerLocal")
    private StepControllerLocal stepControllerLocal;

    @EJB(name = "CategoryControllerLocal")
    private CategoryControllerLocal categoryControllerLocal;

    @EJB(name = "RecipeControllerLocal")
    private RecipeControllerLocal recipeControllerLocal;
    

    /**
     * Creates a new instance of recipeManagementManagedBean
     */
    private List<Recipe> recipes;
    private List<Recipe> filteredRecipeEntities;
    private Recipe selectedRecipeToView;
    private Recipe newRecipe;
    private Recipe selectedRecipeToUpdate;
    private List<Category> categoriesToUpdate;
    private List<Category> newCategories;
    private DualListModel<Category> newCategoryChoices;
    private DualListModel<Category> updateCategoryChoices;
    private List<Category> categories;
    private Long recipeIdNew;
    private boolean skip;
    private Step newStep;
    private List<Step> steps;
    private Step step;


    public RecipeManagementManagedBean() {
        this.newRecipe= new Recipe();
        this.newStep = new Step();
        this.steps = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        this.recipes = recipeControllerLocal.retrieveAllRecipes();

        this.categories = categoryControllerLocal.retrieveAllCategories();
        this.newCategories = new ArrayList<>();

        newCategoryChoices = new DualListModel<Category>(categories, newCategories);

    }

    public Step getNewStep() {
        return newStep;
    }

    public void setNewStep(Step newStep) {
        this.newStep = newStep;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Step getstep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    
    
    public List<Category> getCategories() {
        return categories;
    }

    public void createNewRecipe(ActionEvent event) {

        try {
            Recipe recipe = recipeControllerLocal.createNewRecipe(newRecipe);

            for (Category category : newCategoryChoices.getTarget()) {
                Category temp = categoryControllerLocal.retrieveCategoryById(category.getCategoryId());
                temp.getRecipes().add(recipe);
                recipe.getCategories().add(temp);
            }
            
            for (Step s: steps) {
                recipe.getSteps().add(s);
            }
            try {
                recipeControllerLocal.updateRecipe(recipe);
            } catch (RecipeNotFoundException ex) {
            System.out.println("Recipe not found!");
            }
            recipes.add(recipe);
            this.newCategories = new ArrayList<>();

            newCategoryChoices = new DualListModel<Category>(categories, newCategories);
            newRecipe = new Recipe();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Recipe created successfully (Recipe ID: " + recipe.getRecipeId() + ")", null));
        } catch (CategoryNotFoundException ex) {
            System.out.println("Category not found!");
        }
    }

    public void deleteRecipe(ActionEvent event) {

        try {
            Recipe recipeToDelete = (Recipe) event.getComponent().getAttributes().get("recipeToDelete");
            recipeControllerLocal.deleteRecipe(recipeToDelete.getRecipeId());

            recipes.remove(recipeToDelete);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Recipe deleted successfully", null));
        } catch (RecipeNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void doUpdateRecipe(ActionEvent event) {
        selectedRecipeToUpdate = (Recipe) event.getComponent().getAttributes().get("recipeToUpdate");
    }

    public void updateRecipe(ActionEvent event) {

        try {
            recipeControllerLocal.updateRecipe(selectedRecipeToUpdate);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Category updated successfully", null));
        } catch (RecipeNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public RecipeControllerLocal getRecipeControllerLocal() {
        return recipeControllerLocal;
    }

    public void setRecipeControllerLocal(RecipeControllerLocal recipeControllerLocal) {
        this.recipeControllerLocal = recipeControllerLocal;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<Recipe> getFilteredRecipeEntities() {
        return filteredRecipeEntities;
    }

    public void setFilteredRecipeEntities(List<Recipe> filteredRecipeEntities) {
        this.filteredRecipeEntities = filteredRecipeEntities;
    }

    public Recipe getSelectedRecipeToView() {
        return selectedRecipeToView;
    }

    public void setSelectedRecipeToView(Recipe selectedRecipeToView) {
        this.selectedRecipeToView = selectedRecipeToView;
    }

    public Recipe getNewRecipe() {
        return newRecipe;
    }

    public void setNewRecipe(Recipe newRecipe) {
        this.newRecipe = newRecipe;
    }

    public Recipe getSelectedRecipeToUpdate() {
        return selectedRecipeToUpdate;
    }

    public void setSelectedRecipeToUpdate(Recipe selectedRecipeToUpdate) {
        this.selectedRecipeToUpdate = selectedRecipeToUpdate;
    }

    public List<Category> getCategoriesToUpdate() {
        return categoriesToUpdate;
    }

    public void setCategoriesToUpdate(List<Category> categoriesToUpdate) {
        this.categoriesToUpdate = categoriesToUpdate;
    }

    public List<Category> getNewCategories() {
        return newCategories;
    }

    public void setNewCategories(List<Category> newCategories) {
        this.newCategories = newCategories;
    }

    public DualListModel<Category> getNewCategoryChoices() {
        return newCategoryChoices;
    }

    public void setNewCategoryChoices(DualListModel<Category> newCategoryChoices) {
        this.newCategoryChoices = newCategoryChoices;
    }

    public DualListModel<Category> getUpdateCategoryChoices() {
        return updateCategoryChoices;
    }

    public void setUpdateCategoryChoices(DualListModel<Category> updateCategoryChoices) {
        this.updateCategoryChoices = updateCategoryChoices;
    }

    public Long getRecipeIdNew() {
        return recipeIdNew;
    }

    public void setRecipeIdNew(Long recipeIdNew) {
        this.recipeIdNew = recipeIdNew;
    }

    public String onFlowProcess(FlowEvent event) {
        if(skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        }
        else {
            return event.getNewStep();
        }
    }
    
     public void handleFileUploadRecipe(FileUploadEvent event)
    {
        try
        {
            String storedpath = event.getFile().getFileName();
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + storedpath;



            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();

            while (true)
            {
                a = inputStream.read(buffer);

                if (a < 0)
                {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();
            newRecipe.setImage(storedpath);
//            saveRecipe(newRecipe);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,  "File uploaded successfully", ""));
        }
        catch(IOException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  "File upload error: " + ex.getMessage(), ""));
        }
    }

     public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
    }
     
    public void onUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
    }
     
    public void onReorder() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    }
   
    public void createNewStep(ActionEvent event) 
    {
        System.err.println("********* Create new step");

            Step step = stepControllerLocal.createNewStep(newStep);
            steps.add(step);
            
            newStep = new Step();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Recipe created successfully (Recipe ID: " + step.getStepId() + ")", null));
    }

        public void handleFileUploadStep(FileUploadEvent event)
    {
        try
        {
            String storedpath = event.getFile().getFileName();
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + storedpath;



            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();

            while (true)
            {
                a = inputStream.read(buffer);

                if (a < 0)
                {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();
            newStep.setImageSrc(storedpath);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,  "File uploaded successfully", ""));
        }
        catch(IOException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,  "File upload error: " + ex.getMessage(), ""));
        }
    }

}
