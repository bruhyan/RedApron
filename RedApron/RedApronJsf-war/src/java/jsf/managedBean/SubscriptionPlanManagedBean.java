/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Category;
import entity.Recipe;
import entity.Subscriber;
import entity.SubscriptionPlan;
import enumeration.DeliveryDay;
import enumeration.SubscriptionPlanStatus;
import exceptions.SubscriptionPlanNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import stateless.CategoryControllerLocal;
import stateless.RecipeControllerLocal;
import stateless.SubscriberControllerLocal;
import stateless.SubscriptionPlanControllerLocal;
import stateless.TransactionControllerLocal;

/**
 *
 * @author Bryan
 */
@Named(value = "subscriptionPlanManagedBean")
@ViewScoped
public class SubscriptionPlanManagedBean implements Serializable {

    public SubscriptionPlanStatus getUpdatePlanStatus() {
        return updatePlanStatus;
    }

    @EJB(name = "RecipeControllerLocal")
    private RecipeControllerLocal recipeControllerLocal;

    @EJB(name = "CategoryControllerLocal")
    private CategoryControllerLocal categoryControllerLocal;

    @EJB(name = "SubscriberControllerLocal")
    private SubscriberControllerLocal subscriberControllerLocal;

    @EJB(name = "TransactionControllerLocal")
    private TransactionControllerLocal transactionControllerLocal;

    @EJB(name = "SubscriptionPlanControllerLocal")
    private SubscriptionPlanControllerLocal subscriptionPlanControllerLocal;

    private List<SubscriptionPlan> subscriptionPlans;
    private List<SubscriptionPlan> filteredSubscriptionPlans;
    private SubscriptionPlan newSubscriptionPlan;
    private SubscriptionPlan selectedSubscriptionPlanToView;
    private SubscriptionPlan selectedSubscriptionPlanToUpdate;

    private List<Subscriber> subscribers;
    private Long newSubscriberId;
    private Long subscriberIdUpdate;

    private List<Recipe> recipeList;
    private List<String> recipeIdsStringNew;

    private List<Category> categoryList;
    private Long newCategoryId;
    private Long categoryIdUpdate;

    private SubscriptionPlanStatus updatePlanStatus;
    private DeliveryDay updateDeliveryDay;

    /**
     * Creates a new instance of SubscriptionPlanManagedBean
     */
    public SubscriptionPlanManagedBean() {
        subscriptionPlans = new ArrayList();
        newSubscriptionPlan = new SubscriptionPlan();
        recipeList = new ArrayList();
        recipeIdsStringNew = new ArrayList();
        categoryList = new ArrayList();

    }

    @PostConstruct
    public void postConstruct() {
        subscriptionPlans = subscriptionPlanControllerLocal.retrieveAllSubscriptionPlans();
        subscribers = subscriberControllerLocal.retrieveAllSubscribers();
        recipeList = recipeControllerLocal.retrieveAllRecipes();
        categoryList = categoryControllerLocal.retrieveAllCategories();
    }

    public List<SubscriptionPlan> getSubscriptionPlans() {
        return subscriptionPlans;
    }

    public void setSubscriptionPlans(List<SubscriptionPlan> subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
    }

    public void viewPlanDetails(ActionEvent event) throws IOException {
        Long planIdToView = (Long) event.getComponent().getAttributes().get("planId");
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("planIdToView", planIdToView);
        FacesContext.getCurrentInstance().getExternalContext().redirect("viewPlanDetails.xhtml");
    }

    public void createNewPlan(ActionEvent event) {
        SubscriptionPlan pl = getNewSubscriptionPlan();
        LocalDate start = LocalDate.of(pl.getStartDate().getYear(), pl.getStartDate().getMonth(), pl.getStartDate().getDate());
        LocalDate end = LocalDate.of(pl.getEndDate().getYear(), pl.getEndDate().getMonth(), pl.getEndDate().getDate());
        int numWeeks = (int) ChronoUnit.WEEKS.between(start, end);
//        int numRecipes = recipeIdsStringNew.size();
        pl.setNumOfWeeks(numWeeks);
//        pl.setNumOfRecipes(numRecipes);

        List<Long> recipeIdsNew = null;
        if (recipeIdsStringNew != null && (!recipeIdsStringNew.isEmpty())) {
            recipeIdsNew = new ArrayList();
            for (String recipeIdString : recipeIdsStringNew) {
                recipeIdsNew.add(Long.valueOf(recipeIdString));
            }
        }

        pl = subscriptionPlanControllerLocal.createSubscriptionPlan2(getNewSubscriptionPlan(), newSubscriberId, newCategoryId);

        subscriptionPlans.add(pl);
        setNewSubscriptionPlan(new SubscriptionPlan());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New plan created successfully (Plan ID: " + pl.getSubscriptionPlanId() + ")", null));
    }

    public void doUpdateSubscriptionPlan(ActionEvent event) {
        selectedSubscriptionPlanToUpdate = (SubscriptionPlan) event.getComponent().getAttributes().get("subscriptionPlanToUpdate");

        categoryIdUpdate = selectedSubscriptionPlanToUpdate.getCatergory().getCategoryId();
        subscriberIdUpdate = selectedSubscriptionPlanToUpdate.getSubscriber().getSubscriberId();
    }

    public void updateSubscriptionPlan(ActionEvent event) {
        LocalDate start = LocalDate.of(selectedSubscriptionPlanToUpdate.getStartDate().getYear(), selectedSubscriptionPlanToUpdate.getStartDate().getMonth(), selectedSubscriptionPlanToUpdate.getStartDate().getDate());
        LocalDate end = LocalDate.of(selectedSubscriptionPlanToUpdate.getEndDate().getYear(), selectedSubscriptionPlanToUpdate.getEndDate().getMonth(), selectedSubscriptionPlanToUpdate.getEndDate().getDate());
        int numWeeks = (int) ChronoUnit.WEEKS.between(start, end);
//        int numRecipes = recipeIdsStringNew.size();
        selectedSubscriptionPlanToUpdate.setStatus(updatePlanStatus);
        System.out.println("new status "+selectedSubscriptionPlanToUpdate.getStatus());
        selectedSubscriptionPlanToUpdate.setDeliveryDay(updateDeliveryDay);
        selectedSubscriptionPlanToUpdate.setNumOfWeeks(numWeeks);
//        selectedSubscriptionPlanToUpdate.setNumOfRecipes(numRecipes);
        if (categoryIdUpdate == 0) {
            categoryIdUpdate = null;
        }
        if (subscriberIdUpdate == 0) {
            subscriberIdUpdate = null;
        }

        subscriptionPlanControllerLocal.updatePlan(selectedSubscriptionPlanToUpdate, categoryIdUpdate, subscriberIdUpdate);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Plan updated successfully", null));
    }

    public void deleteSubscriptionPlan(ActionEvent event) {
        SubscriptionPlan planToDelete = (SubscriptionPlan) event.getComponent().getAttributes().get("subscriptionPlanToDelete");
        try {
            subscriptionPlanControllerLocal.deleteSubscriptionPlan(planToDelete.getSubscriptionPlanId());
            subscriptionPlans.remove(planToDelete);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Product deleted successfully", null));
        } catch (SubscriptionPlanNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while deleting product: " + ex.getMessage(), null));
        }

    }

    public SubscriptionPlan getNewSubscriptionPlan() {
        return newSubscriptionPlan;
    }

    public void setNewSubscriptionPlan(SubscriptionPlan newSubscriptionPlan) {
        this.newSubscriptionPlan = newSubscriptionPlan;
    }

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Subscriber> subscribers) {
        this.subscribers = subscribers;
    }

    public Long getNewSubscriberId() {
        return newSubscriberId;
    }

    public void setNewSubscriberId(Long newSubscriberId) {
        this.newSubscriberId = newSubscriberId;
    }

    public SelectItem[] getDeliveryDayValues() {
        SelectItem[] items = new SelectItem[DeliveryDay.values().length];
        int i = 0;
        for (DeliveryDay d : DeliveryDay.values()) {
            items[i++] = new SelectItem(d, d.getLabel());
        }
        return items;
    }

    public SelectItem[] getStatusValues() {
        SelectItem[] items = new SelectItem[SubscriptionPlanStatus.values().length];
        int i = 0;
        for (SubscriptionPlanStatus s : SubscriptionPlanStatus.values()) {
            items[i++] = new SelectItem(s, s.getLabel());
        }
        return items;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
    }

    public void setRecipeList(List<Recipe> recipeList) {
        this.recipeList = recipeList;
    }

    public List<String> getRecipeIdsStringNew() {
        return recipeIdsStringNew;
    }

    public void setRecipeIdsStringNew(List<String> recipeIdsStringNew) {
        this.recipeIdsStringNew = recipeIdsStringNew;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Long getNewCategoryId() {
        return newCategoryId;
    }

    public void setNewCategoryId(Long newCategoryId) {
        this.newCategoryId = newCategoryId;
    }

    public SubscriptionPlan getSelectedSubscriptionPlanToView() {
        return selectedSubscriptionPlanToView;
    }

    public void setSelectedSubscriptionPlanToView(SubscriptionPlan selectedSubscriptionPlanToView) {
        this.selectedSubscriptionPlanToView = selectedSubscriptionPlanToView;
    }

    public SubscriptionPlan getSelectedSubscriptionPlanToUpdate() {
        return selectedSubscriptionPlanToUpdate;
    }

    public void setSelectedSubscriptionPlanToUpdate(SubscriptionPlan selectedSubscriptionPlanToUpdate) {
        this.selectedSubscriptionPlanToUpdate = selectedSubscriptionPlanToUpdate;
    }

    public Long getSubscriberIdUpdate() {
        return subscriberIdUpdate;
    }

    public void setSubscriberIdUpdate(Long subscriberIdUpdate) {
        this.subscriberIdUpdate = subscriberIdUpdate;
    }

    public Long getCategoryIdUpdate() {
        return categoryIdUpdate;
    }

    public void setCategoryIdUpdate(Long categoryIdUpdate) {
        this.categoryIdUpdate = categoryIdUpdate;
    }

    public List<SubscriptionPlan> getFilteredSubscriptionPlans() {
        return filteredSubscriptionPlans;
    }

    public void setFilteredSubscriptionPlans(List<SubscriptionPlan> filteredSubscriptionPlans) {
        this.filteredSubscriptionPlans = filteredSubscriptionPlans;
    }

    public SubscriptionPlanStatus getSubscriptionPlanStatusOngoing() {
        return getUpdatePlanStatus().ONGOING;
    }

    public SubscriptionPlanStatus getSubscriptionPlanStatusCancelled() {
        return getUpdatePlanStatus().CANCELLED;
    }

    public SubscriptionPlanStatus getSubscriptionPlanStatusCompleted() {
        return getUpdatePlanStatus().COMPLETED;
    }

    public void setUpdatePlanStatus(SubscriptionPlanStatus updatePlanStatus) {
        this.updatePlanStatus = updatePlanStatus;
    }

    public DeliveryDay getUpdateDeliveryDay() {
        return updateDeliveryDay;
    }

    public void setUpdateDeliveryDay(DeliveryDay updateDeliveryDay) {
        this.updateDeliveryDay = updateDeliveryDay;
    }

}
