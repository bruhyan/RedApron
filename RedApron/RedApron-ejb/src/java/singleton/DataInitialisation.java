/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singleton;

import entity.Category;
import entity.Staff;
import entity.Subscriber;
import entity.SubscriptionPlan;
import entity.Transaction;
import enumeration.DeliveryDay;
import enumeration.Role;
import enumeration.SubscriptionPlanStatus;
import exceptions.StaffNotFoundException;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import stateless.CategoryControllerLocal;
import stateless.RecipeControllerLocal;
import stateless.StaffControllerLocal;
import stateless.SubscriberControllerLocal;
import stateless.SubscriptionPlanControllerLocal;
import stateless.TransactionControllerLocal;

/**
 *
 * @author matthealoo
 */
@Singleton
@LocalBean
@Startup
public class DataInitialisation {

    @EJB
    private RecipeControllerLocal recipeControllerLocal;

    @EJB(name = "CategoryControllerLocal")
    private CategoryControllerLocal categoryControllerLocal;
    
    @EJB(name = "TransactionControllerLocal")
    private TransactionControllerLocal transactionControllerLocal;

    @EJB(name = "SubscriptionPlanControllerLocal")
    private SubscriptionPlanControllerLocal subscriptionPlanControllerLocal;
    
    @EJB(name = "SubscriberControllerLocal")
    private SubscriberControllerLocal subscriberControllerLocal;
    
    @EJB
    private StaffControllerLocal staffController;
    
    @PersistenceContext(unitName = "RedApron-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public DataInitialisation() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        try
        {
            staffController.retrieveStaffByEmail("systemadmin@redapron.com");
        }
        catch(StaffNotFoundException ex)
        {
            initializeData();
        }
    }

    private void initializeData() {
        
        staffController.createNewStaff(new Staff("test1","one", "systemadmin@redapron.com", "password",Role.SYSTEM_ADMIN));
        staffController.createNewStaff(new Staff("test2","two", "custsupp@redapron.com", "password", Role.CUSTOMER_SUPPORT));
        staffController.createNewStaff(new Staff("test3","three", "prodman@redapron.com", "password", Role.PRODUCT_MANAGER));
        
        subscriberControllerLocal.createNewSubscriber(new Subscriber("Alpha", "Tan", "alpha@gmail.com", "90000001", "kent ridge", "corner1", 000001, "password"));
        subscriberControllerLocal.createNewSubscriber(new Subscriber("Kenny", "Tan", "kenny@gmail.com", "90000002", "kent ridge", "corner2", 000002, "password"));
        subscriberControllerLocal.createNewSubscriber(new Subscriber("Bady", "Tan", "bady@gmail.com", "90000003", "kent ridge", "corner3", 000003, "password"));
        
        subscriptionPlanControllerLocal.createSubscriptionPlan(new SubscriptionPlan(new Date(2019,4,1), new Date(2019,6,1), "No shrimp i die", 9, 1, SubscriptionPlanStatus.ONGOING, DeliveryDay.MONDAY));
        subscriptionPlanControllerLocal.createSubscriptionPlan(new SubscriptionPlan(new Date(2019,4,1), new Date(2019,6,1), "No shrimp i die", 9, 1, SubscriptionPlanStatus.ONGOING, DeliveryDay.MONDAY));
        subscriptionPlanControllerLocal.createSubscriptionPlan(new SubscriptionPlan(new Date(2019,4,1), new Date(2019,6,1), "No shrimp i die", 9, 1, SubscriptionPlanStatus.ONGOING, DeliveryDay.MONDAY));
        
        categoryControllerLocal.createNewCategory(new Category("Free Style For 2", 40.00, true));
        categoryControllerLocal.createNewCategory(new Category("Free Style For 4", 60.00, true));
        categoryControllerLocal.createNewCategory(new Category("Signature For 2", 40.00, true));
        categoryControllerLocal.createNewCategory(new Category("Signature For 4", 60.00, true));
        categoryControllerLocal.createNewCategory(new Category("Vegeterian For 2", 35.00, true));
        categoryControllerLocal.createNewCategory(new Category("Vegeterian For 4", 52.00, true));
        categoryControllerLocal.createNewCategory(new Category("Healthy Living For 2", 45.00, true));
        categoryControllerLocal.createNewCategory(new Category("Healthy Living For 4", 62.00, true));
        
        recipeController
        
        
        transactionControllerLocal.createNewTransaction(new Transaction());
        
        
        
        
    }

    public void persist(Object object) {
        em.persist(object);
    }
    
    
    
}
