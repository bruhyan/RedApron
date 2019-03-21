/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Category;
import entity.Recipe;
import entity.Subscriber;
import entity.SubscriptionPlan;
import exceptions.CategoryNotFoundException;
import exceptions.RecipeNotFoundException;
import exceptions.SubscriberNotFoundException;
import exceptions.SubscriptionPlanNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author matthealoo
 */
@Stateless
public class SubscriptionPlanController implements SubscriptionPlanControllerLocal {

    @EJB(name = "CategoryControllerLocal")
    private CategoryControllerLocal categoryControllerLocal;

    @EJB(name = "SubscriberControllerLocal")
    private SubscriberControllerLocal subscriberControllerLocal;
    
    @EJB(name = "RecipeControllerLocal")
    private RecipeControllerLocal recipeControllerLocal;
    
    @PersistenceContext(unitName = "RedApron-ejbPU")
    private EntityManager em;
    

    public SubscriptionPlanController() {
    }
    
    @Override
    public SubscriptionPlan createSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        em.persist(subscriptionPlan);
        em.flush();
        return subscriptionPlan;
    }
    
    @Override
    public SubscriptionPlan createSubscriptionPlan2(SubscriptionPlan subscriptionPlan, Long subscriberId, Long categoryId) {
        /*for(Long recipeId : recipeIds) {
            try {
                Recipe recipe = recipeControllerLocal.retrieveRecipeById(recipeId);
                subscriptionPlan.getRecipes().add(recipe);
            } catch (RecipeNotFoundException ex) {
                Logger.getLogger(SubscriptionPlanController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }*/
        try {
            Subscriber subscriber = subscriberControllerLocal.retrieveSubscriberById(subscriberId);
            subscriptionPlan.setSubscriber(subscriber);
        } catch (SubscriberNotFoundException ex) {
            Logger.getLogger(SubscriptionPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Category category = categoryControllerLocal.retrieveCategoryById(categoryId);
            subscriptionPlan.setCatergory(category);
        } catch (CategoryNotFoundException ex) {
            Logger.getLogger(SubscriptionPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        em.persist(subscriptionPlan);
        em.flush();
        return subscriptionPlan;
    }
    
    @Override
    public List<SubscriptionPlan> retrieveAllSubscriptionPlans () {
        Query query = em.createQuery("SELECT s FROM SubscriptionPlan s");
        List<SubscriptionPlan> plans = query.getResultList();
        for (SubscriptionPlan plan : plans) {
            plan.getSubscriber();
            plan.getCatergory();
            plan.getTransaction();
            plan.getRecipes().size();
        }
        return plans;
    }
    
    public SubscriptionPlan retrieveSubscriptionPlanById(Long subscriptionPlanId) throws SubscriptionPlanNotFoundException {
        if (subscriptionPlanId == null) {
            
        }
        
        SubscriptionPlan plan = em.find(SubscriptionPlan.class, subscriptionPlanId);
        
        if(plan != null) {
            plan.getSubscriber();
            plan.getCatergory();
            plan.getTransaction();
            plan.getRecipes().size();
        }
        return plan;
    }
    
    public List<SubscriptionPlan> retrieveSubscriptionPlanBySubscriberId(Long subscriberId) {
        Query query = em.createQuery("SELECT s FROM SubscriptionPlan s WHERE s.subscriber.subscriberId = :subscriberId");
        List<SubscriptionPlan> plans = query.getResultList();
        for (SubscriptionPlan plan : plans) {
            plan.getSubscriber();
            plan.getCatergory();
            plan.getTransaction();
            plan.getRecipes().size();
        }
        return plans;
    }
    
    public void deleteSubscriptionPlan(Long subscriptionPlanId) throws SubscriptionPlanNotFoundException {
        //dissociate all 2 ways first
        //incomplete
        SubscriptionPlan plan = retrieveSubscriptionPlanById(subscriptionPlanId);
        em.remove(plan);
    }
    
    
    
    
    
    
}
