/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.SubscriptionPlan;
import exceptions.SubscriptionPlanNotFoundException;
import java.util.List;
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
