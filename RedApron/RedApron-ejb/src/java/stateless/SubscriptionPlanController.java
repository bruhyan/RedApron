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
import exceptions.SubscriberNotFoundException;
import exceptions.SubscriptionPlanNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
        em.persist(subscriptionPlan);
        try {
            Subscriber subscriber = subscriberControllerLocal.retrieveSubscriberById(subscriberId);
            subscriber.getSubscriptionPlans().add(subscriptionPlan);
            subscriptionPlan.setSubscriber(subscriber);

        } catch (SubscriberNotFoundException ex) {
            Logger.getLogger(SubscriptionPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Category category = categoryControllerLocal.retrieveCategoryById(categoryId);
            subscriptionPlan.setCatergory(category);
            List<Recipe> recipes = new ArrayList<>();
            List<Recipe> recipesChosen = new ArrayList<>();
            recipes = categoryControllerLocal.retrieveRecipesByCategoryId(categoryId);
            System.out.println("Hello");
            for (int i = 0; i < subscriptionPlan.getNumOfRecipes(); i++) {
                Random rand = new Random();
                int index = rand.nextInt(recipes.size());
                System.out.println(index);
                recipesChosen.add(recipes.get(index));
            }

            subscriptionPlan.setRecipes(recipesChosen);

        } catch (CategoryNotFoundException ex) {
            Logger.getLogger(SubscriptionPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }

        em.flush();
        return subscriptionPlan;
    }

    @Override
    public List<SubscriptionPlan> retrieveLatestSubscriptionPlan(int num) {
        Query query = em.createQuery("SELECT s FROM SubscriptionPlan s ORDER BY s.subscriptionPlanId DESC");
        query.setMaxResults(num);
        return query.getResultList();

    }

    @Override
    public void updatePlan(SubscriptionPlan plan, Long categoryIdUpdate, Long subscriberIdUpdate) {
        SubscriptionPlan planToUpdate;
        try {
            planToUpdate = retrieveSubscriptionPlanById(plan.getSubscriptionPlanId());
            if (categoryIdUpdate != null) {
                try {
                    Category cat = categoryControllerLocal.retrieveCategoryById(categoryIdUpdate);
                    Category prev = planToUpdate.getCatergory();
                    prev.getSubscriptionPlans().remove(planToUpdate);
                    planToUpdate.setCatergory(cat);
                    cat.getSubscriptionPlans().add(planToUpdate);
                } catch (CategoryNotFoundException ex) {
                    Logger.getLogger(SubscriptionPlanController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (subscriberIdUpdate != null) {
                try {
                    Subscriber newSub = subscriberControllerLocal.retrieveSubscriberById(subscriberIdUpdate);
                    Subscriber prevSub = planToUpdate.getSubscriber();
                    prevSub.getSubscriptionPlans().remove(planToUpdate);
                    planToUpdate.setSubscriber(newSub);
                } catch (SubscriberNotFoundException ex) {
                    Logger.getLogger(SubscriptionPlanController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            planToUpdate.setStartDate(plan.getStartDate());
            planToUpdate.setEndDate(plan.getEndDate());
            planToUpdate.setPreferences(plan.getPreferences());
            planToUpdate.setNumOfWeeks(plan.getNumOfWeeks());
            planToUpdate.setNumOfRecipes(plan.getNumOfRecipes());
        } catch (SubscriptionPlanNotFoundException ex) {
            Logger.getLogger(SubscriptionPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public List<SubscriptionPlan> retrieveAllSubscriptionPlans() {
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

    @Override
    public SubscriptionPlan retrieveSubscriptionPlanById(Long subscriptionPlanId) throws SubscriptionPlanNotFoundException {
        if (subscriptionPlanId == null) {

        }

        SubscriptionPlan plan = em.find(SubscriptionPlan.class, subscriptionPlanId);

        if (plan != null) {
            plan.getSubscriber();
            plan.getCatergory();
            plan.getTransaction();
            plan.getRecipes().size();
        }
        return plan;
    }

    @Override
    public List<SubscriptionPlan> retrieveSubscriptionPlanBySubscriberId(Long subscriberId) {
        Query query = em.createQuery("SELECT s FROM SubscriptionPlan s WHERE s.subscriber.subscriberId =:inSubscriberId");
        query.setParameter("inSubscriberId", subscriberId);
        List<SubscriptionPlan> plans = query.getResultList();
        for (SubscriptionPlan plan : plans) {
            plan.getSubscriber();
            plan.getCatergory();
            plan.getTransaction();
            plan.getRecipes().size();
        }
        return plans;
    }

    @Override
    public List<Recipe> retrieveRecipesBySubscriptionPlanId(Long id) {
        try {
            SubscriptionPlan sp = retrieveSubscriptionPlanById(id);
            sp.getRecipes().size();
            return sp.getRecipes();
        } catch (SubscriptionPlanNotFoundException ex) {
            Logger.getLogger(SubscriptionPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public void deleteSubscriptionPlan(Long subscriptionPlanId) throws SubscriptionPlanNotFoundException {
        //dissociate all 2 ways first
        //incomplete
        SubscriptionPlan plan = retrieveSubscriptionPlanById(subscriptionPlanId);
        Long planSubscriberId = plan.getSubscriber().getSubscriberId();
        Subscriber planSubscriber;
        try {
            planSubscriber = subscriberControllerLocal.retrieveSubscriberById(planSubscriberId);
            planSubscriber.getSubscriptionPlans().remove(plan);
        } catch (SubscriberNotFoundException ex) {
            Logger.getLogger(SubscriptionPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Long categoryId = plan.getCatergory().getCategoryId();
        Category cat;
        try {
            cat = categoryControllerLocal.retrieveCategoryById(categoryId);
            cat.getSubscriptionPlans().remove(plan);
        } catch (CategoryNotFoundException ex) {
            Logger.getLogger(SubscriptionPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        em.remove(plan);
    }

    //Analytic 1 Number of subscriptionplan
    //SELECT * FROM SubscriptionPlan WHERE startDate >= '3919-04-30 16:00:00.000' and startDate <= '3919-04-30 16:00:00.000';
    @Override
    public List<SubscriptionPlan> retrieveSubscriptionPlanByDateRange(Date date1, Date date2) {
        Query query = em.createQuery("SELECT s FROM SubscriptionPlan s WHERE startDate >= :inDate1 and startDate <= :inDate2");
        query.setParameter("inDate1", date1);
        query.setParameter("inDate2", date2);

        List<SubscriptionPlan> plans = query.getResultList();
        for (SubscriptionPlan plan : plans) {
            plan.getSubscriber();
            plan.getCatergory();
            plan.getTransaction();
            plan.getRecipes().size();
        }
        return plans;
    }

    //Analytic 1 Number of subscriptionplan
    //SELECT * FROM SubscriptionPlan WHERE startDate <= '2019-04-30 00:00:00';
    @Override
    public List<SubscriptionPlan> retrieveSubscriptionPlanByDate(Date date1) {
        Query query = em.createQuery("SELECT s FROM SubscriptionPlan s WHERE s.startDate <= :inDate1");
        query.setParameter("inDate1", date1);

        List<SubscriptionPlan> plans = query.getResultList();
        for (SubscriptionPlan plan : plans) {
            plan.getSubscriber();
            plan.getCatergory();
            plan.getTransaction();
            plan.getRecipes().size();
        }
        return plans;
    }

}
