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
import exceptions.StepNotFoundException;
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
    private List<Category> categories;
    private Long recipeIdNew;
    private boolean skip;
    private Step newStep;
    private List<Step> steps;
    private Step step;
    private List<Step> orderedSteps;
    private List<Category> oldCategories;
    private List<Step> stepsToDelete;

    public RecipeManagementManagedBean() {
        this.newRecipe = new Recipe();
        this.newStep = new Step();
        this.stepsToDelete = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct() {
        this.recipes = recipeControllerLocal.retrieveAllRecipes();

        this.categories = categoryControllerLocal.retrieveAllCategories();
        this.newCategories = new ArrayList<>();

        newCategoryChoices = new DualListModel<Category>(categories, newCategories);

    }

    public List<Step> getOrderedSteps() {
        return orderedSteps;
    }

    public void setOrderedSteps(List<Step> orderedSteps) {
        this.orderedSteps = orderedSteps;
    }

    public List<Category> getOldCategories() {
        return oldCategories;
    }

    public void setOldCategories(List<Category> oldCategories) {
        this.oldCategories = oldCategories;
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

    public List<Step> getStepsToDelete() {
        return stepsToDelete;
    }

    public void setStepsToDelete(List<Step> stepsToDelete) {
        this.stepsToDelete = stepsToDelete;
    }

    public void createNewRecipe(ActionEvent event) {

        try {
            Recipe recipe = recipeControllerLocal.createNewRecipe(newRecipe);
            System.out.println(newCategoryChoices.getTarget());
            for (Category category : newCategoryChoices.getTarget()) {
                Category temp = categoryControllerLocal.retrieveCategoryById(category.getCategoryId());
                temp.getRecipes().size();
                temp.getRecipes().add(recipe);
                recipe.getCategories().size();
                recipe.getCategories().add(temp);
                System.out.println("adding category " + temp.getName() + "to recipe");
                categoryControllerLocal.updateCategory(temp);
                recipeControllerLocal.updateRecipe(recipe);

            }
            int i = 0;
            for (Step s : steps) {

                System.out.println("adding step: " + s.getInstruction());
                s.setOrderNum(i);
                recipe.getSteps().add(s);
                i++;
                stepControllerLocal.updateStep(s);
            }

            recipeControllerLocal.updateRecipe(recipe);

            recipes.add(recipe);
            this.newCategories = new ArrayList<>();

            newCategoryChoices = new DualListModel<Category>(categories, newCategories);
            newRecipe = new Recipe();

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Recipe created successfully (Recipe ID: " + recipe.getRecipeId() + ")", null));
        } catch (CategoryNotFoundException ex) {
            System.out.println("Category not found!");
        } catch (RecipeNotFoundException ex) {
            System.out.println("Recipe not found!");

        } catch (StepNotFoundException ex) {
            System.out.println("Step not found!");

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

    public void updateRecipe(ActionEvent event) {

        try {
            recipeControllerLocal.updateRecipe(selectedRecipeToUpdate);

            for (Category c : oldCategories) { //need to remove old categories
                Category temp = categoryControllerLocal.retrieveCategoryById(c.getCategoryId());
                temp.getRecipes().size();
                temp.getRecipes().remove(selectedRecipeToUpdate);
                categoryControllerLocal.updateCategory(temp); //update the old category cos just removed recipe

            }
            selectedRecipeToUpdate.getCategories().clear(); //remove all categories from recipe to be updated

            for (Category category : newCategoryChoices.getTarget()) { //adding new categories identified by the picklist
                Category temp = categoryControllerLocal.retrieveCategoryById(category.getCategoryId());
                temp.getRecipes().size();
                temp.getRecipes().add(selectedRecipeToUpdate); //add this recipe to the category
                selectedRecipeToUpdate.getCategories().size();
                selectedRecipeToUpdate.getCategories().add(temp); //add category to the recipe
                System.out.println("adding category " + temp.getName() + "to recipe");
                categoryControllerLocal.updateCategory(temp);
                recipeControllerLocal.updateRecipe(selectedRecipeToUpdate);

            }
            int i = 0;
            for (Step s : stepsToDelete) {
                try {
                    selectedRecipeToUpdate.getSteps().remove(s);
                    recipeControllerLocal.updateRecipe(selectedRecipeToUpdate);

                    stepControllerLocal.deleteStep(s);
                    System.out.println("deleting step..." + s.getStepId());
                } catch (StepNotFoundException ex) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Step not found: " + ex.getMessage(), ""));
                }
            }
            selectedRecipeToUpdate.getSteps().clear();
            for (Step s : orderedSteps) {

                System.out.println("adding step: " + s.getInstruction());
                s.setOrderNum(i);
                selectedRecipeToUpdate.getSteps().add(s);
                i++;
                stepControllerLocal.updateStep(s);
            }

            recipeControllerLocal.updateRecipe(selectedRecipeToUpdate);

            recipes.add(selectedRecipeToUpdate);
            this.newCategories = new ArrayList<>();

            newCategoryChoices = new DualListModel<Category>(categories, newCategories);
            orderedSteps = null;

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Recipe updated successfully", null));
        } catch (RecipeNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        } catch (CategoryNotFoundException ex) {
            System.out.println("Category not found!");
        } catch (StepNotFoundException ex) {
            System.out.println("Step not found!");

        }
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
        try {
            this.selectedRecipeToView = selectedRecipeToView;

            setOrderedSteps(recipeControllerLocal.retrieveOrderedStepsByRecipeId(selectedRecipeToView.getRecipeId()));
        } catch (RecipeNotFoundException ex) {
            Logger.getLogger(RecipeManagementManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        try {
            this.selectedRecipeToUpdate = selectedRecipeToUpdate;
            System.out.println("RECIPE ID TO UPDATE : " + selectedRecipeToUpdate.getRecipeId());
            oldCategories = selectedRecipeToUpdate.getCategories();
            setOrderedSteps(recipeControllerLocal.retrieveOrderedStepsByRecipeId(selectedRecipeToUpdate.getRecipeId()));
        } catch (RecipeNotFoundException ex) {
            Logger.getLogger(RecipeManagementManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public Long getRecipeIdNew() {
        return recipeIdNew;
    }

    public void setRecipeIdNew(Long recipeIdNew) {
        this.recipeIdNew = recipeIdNew;
    }

    public String onFlowProcess(FlowEvent event) {
        if (skip) {
            skip = false;   //reset in case user goes back
            return "confirm";
        } else {
            return event.getNewStep();
        }
    }

    public void handleFileUploadRecipe(FileUploadEvent event) {
        try {
            String storedpath = event.getFile().getFileName();
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + storedpath;

            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();

            while (true) {
                a = inputStream.read(buffer);

                if (a < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();
            newRecipe.setImage(storedpath);
//            saveRecipe(newRecipe);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public void updateHandleFileUploadRecipe(FileUploadEvent event) {
        try {
            String storedpath = event.getFile().getFileName();
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + storedpath;

            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();

            while (true) {
                a = inputStream.read(buffer);

                if (a < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();
            selectedRecipeToUpdate.setImage(storedpath);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

    public void onSelect(SelectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Selected", event.getObject().toString()));
        step = (Step) event.getObject();
    }

    public void removeStepFromList(ActionEvent event) {
        stepsToDelete.add(step);
        orderedSteps.remove(step);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Step ID : " + step.getStepId() + " removed", ""));
        step = new Step();
    }

    public void onUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Item Unselected", event.getObject().toString()));
    }

    public void onReorder() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "List Reordered", null));
    }

    public void createNewStep(ActionEvent event) {
        System.err.println("********* Create new step");

        Step step = stepControllerLocal.createNewStep(newStep);
        steps.add(step);

        newStep = new Step();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Step created successfully (Step ID: " + step.getStepId() + ")", null));
    }

    public void createNewStepForUpdate(ActionEvent event) {
        System.err.println("********* Create new step for update");

        Step step = stepControllerLocal.createNewStep(newStep);
        orderedSteps.add(step);

        newStep = new Step();

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Step created successfully (Step ID: " + step.getStepId() + ")", null));
    }

    public void handleFileUploadStep(FileUploadEvent event) {
        try {
            String storedpath = event.getFile().getFileName();
            String newFilePath = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + storedpath;

            File file = new File(newFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            InputStream inputStream = event.getFile().getInputstream();

            while (true) {
                a = inputStream.read(buffer);

                if (a < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            inputStream.close();
            newStep.setImageSrc(storedpath);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File uploaded successfully", ""));
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "File upload error: " + ex.getMessage(), ""));
        }
    }

}
