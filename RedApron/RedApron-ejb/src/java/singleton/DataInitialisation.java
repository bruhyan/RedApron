/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singleton;

import entity.Category;
import entity.Recipe;
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
        
        Subscriber sub1 = subscriberControllerLocal.createNewSubscriber(new Subscriber("Alpha", "Tan", "alpha@gmail.com", "90000001", "kent ridge", "corner1", 000001, "password"));
        Subscriber sub2 = subscriberControllerLocal.createNewSubscriber(new Subscriber("Kenny", "Tan", "kenny@gmail.com", "90000002", "kent ridge", "corner2", 000002, "password"));
        Subscriber sub3 = subscriberControllerLocal.createNewSubscriber(new Subscriber("Bady", "Tan", "bady@gmail.com", "90000003", "kent ridge", "corner3", 000003, "password"));
        
        SubscriptionPlan plan1 = subscriptionPlanControllerLocal.createSubscriptionPlan(new SubscriptionPlan(new Date(2019,4,1), new Date(2019,6,1), "No shrimp i die", 9, 1, SubscriptionPlanStatus.ONGOING, DeliveryDay.MONDAY));
        SubscriptionPlan plan2 = subscriptionPlanControllerLocal.createSubscriptionPlan(new SubscriptionPlan(new Date(2019,4,1), new Date(2019,6,1), "No shrimp i die", 9, 1, SubscriptionPlanStatus.ONGOING, DeliveryDay.MONDAY));
        SubscriptionPlan plan3 = subscriptionPlanControllerLocal.createSubscriptionPlan(new SubscriptionPlan(new Date(2019,4,1), new Date(2019,6,1), "No shrimp i die", 9, 1, SubscriptionPlanStatus.ONGOING, DeliveryDay.MONDAY));
        
        Category cat1 = categoryControllerLocal.createNewCategory(new Category("Free Style For 2", 40.00, true));
        Category cat2 = categoryControllerLocal.createNewCategory(new Category("Free Style For 4", 60.00, true));
        Category cat3 =categoryControllerLocal.createNewCategory(new Category("Signature For 2", 40.00, true));
        Category cat4 =categoryControllerLocal.createNewCategory(new Category("Signature For 4", 60.00, true));
        Category cat5 =categoryControllerLocal.createNewCategory(new Category("Vegeterian For 2", 35.00, true));
        Category cat6 =categoryControllerLocal.createNewCategory(new Category("Vegeterian For 4", 52.00, true));
        Category cat7 =categoryControllerLocal.createNewCategory(new Category("Healthy Living For 2", 45.00, true));
        Category cat8 =categoryControllerLocal.createNewCategory(new Category("Healthy Living For 4", 62.00, true));
        
        Recipe recipe1 = recipeControllerLocal.createNewRecipe(new Recipe("Hainan-style Roasted Chicken", "", "with Steamed Oiled Rice & Fresh Cucumbers", "", true));
        Recipe recipe2 = recipeControllerLocal.createNewRecipe(new Recipe("Smoky Ancho Baked Chicken", "", "with Spiced Rice & Black Beans", "", true)); //860 cal, 35 min
        Recipe recipe3 = recipeControllerLocal.createNewRecipe(new Recipe("Nashville-Style Hot Chicken", "", "with Maple Kale & Mashed Sweet Potatoes", "", true)); //810 calroies, 
        Recipe recipe4 = recipeControllerLocal.createNewRecipe(new Recipe("Calabrian Shrimp & Orzo", "", "with Zucchini", "", true)); //480 calories, 20 mins
        Recipe recipe5 = recipeControllerLocal.createNewRecipe(new Recipe("Goat Cheese & Mushroom Quesadillas", "", "with Lemon-Dressed Zucchini", "", true)); //vegeterian, 590 cal, 45 mins
        Recipe recipe6 = recipeControllerLocal.createNewRecipe(new Recipe("Middle Eastern-Style Pasta", "", "with Roasted Broccoli & Brown Butter-Tomato Sauce", "", true)); //800 cal, 25 mins, vegeterian
        Recipe recipe7 = recipeControllerLocal.createNewRecipe(new Recipe("Vegetable & Freekeh Fried Rice", "", "with Kombu & Peanuts", "", true)); //440 cal, 35 mins, vegeterian
        Recipe recipe8 = recipeControllerLocal.createNewRecipe(new Recipe("Chicken & Curry Mustard", "", "with Carrot & Currant Rice", "", true)); // 590 cal, 25 mins
        
        //setting relation
        //test if object is managed here
        
        plan1.getRecipes().add(recipe1);
        plan2.getRecipes().add(recipe2);
        plan3.getRecipes().add(recipe7);
        plan1.setCatergory(cat1);
        plan2.setCatergory(cat3);
        plan3.setCatergory(cat5);
        cat1.getSubscriptionPlans().add(plan1);
        cat2.getSubscriptionPlans().add(plan2);
        cat3.getSubscriptionPlans().add(plan3);
        cat1.getRecipes().add(recipe1);
        cat3.getRecipes().add(recipe2);
        cat5.getRecipes().add(recipe7);
        recipe1.getCategories().add(cat1);
        recipe2.getCategories().add(cat3);
        recipe3.getCategories().add(cat5);
        sub1.getSubscriptionPlans().add(plan1);
        sub1.getSubscriptionPlans().add(plan2);
        sub1.getSubscriptionPlans().add(plan3);
        plan1.setSubscriber(sub1);
        plan2.setSubscriber(sub1);
        plan3.setSubscriber(sub1);
        //transaction not created and set
        
        
        
        //transactionControllerLocal.createNewTransaction(new Transaction());
        
        
        
        
    }

    public void persist(Object object) {
        em.persist(object);
    }
    
    
    
}
