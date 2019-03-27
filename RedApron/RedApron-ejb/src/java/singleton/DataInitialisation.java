/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singleton;

import entity.Category;
import entity.Enquiry;
import entity.Event;
import entity.Recipe;
import entity.Review;
import entity.Staff;
import entity.Subscriber;
import entity.SubscriptionPlan;
import entity.Transaction;
import enumeration.DeliveryDay;
import enumeration.PaymentType;
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
import stateless.EnquiryControllerLocal;
import stateless.EventControllerLocal;
import stateless.RecipeControllerLocal;
import stateless.ReviewControllerLocal;
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

    @EJB(name = "ReviewControllerLocal")
    private ReviewControllerLocal reviewControllerLocal;

    @EJB
    private EventControllerLocal eventController;

    @EJB(name = "EnquiryControllerLocal") 
    private EnquiryControllerLocal enquiryControllerLocal;

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
    public void postConstruct() {
        try {
            staffController.retrieveStaffByEmail("systemadmin@redapron.com");
        } catch (StaffNotFoundException ex) {
            initializeData();
        }
    }

    private void initializeData() {
        
        Staff staff1 = new Staff("test1", "one", "systemadmin@redapron.com", "password", Role.SYSTEM_ADMIN);
        Staff staff2 = new Staff("test2", "two", "custsupp@redapron.com", "password", Role.CUSTOMER_SUPPORT);
        Staff staff3 = new Staff("test3", "three", "prodman@redapron.com", "password", Role.PRODUCT_MANAGER);
        
        staffController.createNewStaff(staff1);
        staffController.createNewStaff(staff2);
        staffController.createNewStaff(staff3);

        Subscriber sub1 = subscriberControllerLocal.createNewSubscriber(new Subscriber("Alpha", "Tan", "alpha@gmail.com", "90000001", "kent ridge", "corner1", 000001, "password"));
        Subscriber sub2 = subscriberControllerLocal.createNewSubscriber(new Subscriber("Kenny", "Tan", "kenny@gmail.com", "90000002", "kent ridge", "corner2", 000002, "password"));
        Subscriber sub3 = subscriberControllerLocal.createNewSubscriber(new Subscriber("Bady", "Tan", "bady@gmail.com", "90000003", "kent ridge", "corner3", 000003, "password"));

        SubscriptionPlan plan1 = subscriptionPlanControllerLocal.createSubscriptionPlan(new SubscriptionPlan(new Date(2019, 4, 1), new Date(2019, 6, 1), "No shrimp i die", 9, 1, SubscriptionPlanStatus.ONGOING, DeliveryDay.MONDAY));
        SubscriptionPlan plan2 = subscriptionPlanControllerLocal.createSubscriptionPlan(new SubscriptionPlan(new Date(2019, 4, 1), new Date(2019, 6, 1), "No shrimp i die", 9, 1, SubscriptionPlanStatus.ONGOING, DeliveryDay.MONDAY));
        SubscriptionPlan plan3 = subscriptionPlanControllerLocal.createSubscriptionPlan(new SubscriptionPlan(new Date(2019, 4, 1), new Date(2019, 6, 1), "No shrimp i die", 9, 1, SubscriptionPlanStatus.ONGOING, DeliveryDay.MONDAY));

        Category cat1 = categoryControllerLocal.createNewCategory(new Category("Free Style For 2", 40.00, true));
        Category cat2 = categoryControllerLocal.createNewCategory(new Category("Free Style For 4", 60.00, true));
        Category cat3 = categoryControllerLocal.createNewCategory(new Category("Signature For 2", 40.00, true));
        Category cat4 = categoryControllerLocal.createNewCategory(new Category("Signature For 4", 60.00, true));
        Category cat5 = categoryControllerLocal.createNewCategory(new Category("Vegeterian For 2", 35.00, true));
        Category cat6 = categoryControllerLocal.createNewCategory(new Category("Vegeterian For 4", 52.00, true));
        Category cat7 = categoryControllerLocal.createNewCategory(new Category("Healthy Living For 2", 45.00, true));
        Category cat8 = categoryControllerLocal.createNewCategory(new Category("Healthy Living For 4", 62.00, true));

        Recipe recipe1 = recipeControllerLocal.createNewRecipe(new Recipe("Hainan-style Roasted Chicken", "", "with Steamed Oiled Rice & Fresh Cucumbers", "", true));
        Recipe recipe2 = recipeControllerLocal.createNewRecipe(new Recipe("Smoky Ancho Baked Chicken", "", "with Spiced Rice & Black Beans", "", true)); //860 cal, 35 min
        Recipe recipe3 = recipeControllerLocal.createNewRecipe(new Recipe("Nashville-Style Hot Chicken", "", "with Maple Kale & Mashed Sweet Potatoes", "", true)); //810 calroies, 
        Recipe recipe4 = recipeControllerLocal.createNewRecipe(new Recipe("Calabrian Shrimp & Orzo", "", "with Zucchini", "", true)); //480 calories, 20 mins
        Recipe recipe5 = recipeControllerLocal.createNewRecipe(new Recipe("Goat Cheese & Mushroom Quesadillas", "", "with Lemon-Dressed Zucchini", "", true)); //vegeterian, 590 cal, 45 mins
        Recipe recipe6 = recipeControllerLocal.createNewRecipe(new Recipe("Middle Eastern-Style Pasta", "", "with Roasted Broccoli & Brown Butter-Tomato Sauce", "", true)); //800 cal, 25 mins, vegeterian
        Recipe recipe7 = recipeControllerLocal.createNewRecipe(new Recipe("Vegetable & Freekeh Fried Rice", "", "with Kombu & Peanuts", "", true)); //440 cal, 35 mins, vegeterian
        Recipe recipe8 = recipeControllerLocal.createNewRecipe(new Recipe("Chicken & Curry Mustard", "", "with Carrot & Currant Rice", "", true)); // 590 cal, 25 mins

        Transaction transaction1 = transactionControllerLocal.createNewTransaction(new Transaction(40.00, new Date(2019, 3, 10), PaymentType.MASTER));
        Transaction transaction2 = transactionControllerLocal.createNewTransaction(new Transaction(40.00, new Date(2019, 3, 10), PaymentType.VISA));
        Transaction transaction3 = transactionControllerLocal.createNewTransaction(new Transaction(35.00, new Date(2019, 3, 10), PaymentType.PAYPAL));

        Enquiry enquiry1 = enquiryControllerLocal.createNewEnquiry(new Enquiry("Hello, I would like to ask where is my meal?", sub1));
        Enquiry enquiry2 = enquiryControllerLocal.createNewEnquiry(new Enquiry("Give me free food pl0x", sub2));
        Enquiry enquiry3 = enquiryControllerLocal.createNewEnquiry(new Enquiry("Where is the toilet?", sub3));
        
        Review review1 = reviewControllerLocal.createNewReview(new Review("Good, 10/10 would get this again.", 5, new Date(2019-1900, 3, 10), sub1, recipe1));
        Review review2 = reviewControllerLocal.createNewReview(new Review("gr8 b8 m8 i r8 8/8", 5, new Date(2019-1900, 3, 11), sub1, recipe2));
        Review review3 = reviewControllerLocal.createNewReview(new Review("I have had better", 3, new Date(2019-1900, 3, 15), sub3, recipe1));
        
        

        //setting relation
        //test if object is managed here
        
        //subscriber -> review
        sub1.getReviews().add(review1);
        sub2.getReviews().add(review2);
        sub3.getReviews().add(review3);
        
        //recipe -> review
        recipe1.getReviews().add(review1);
        recipe2.getReviews().add(review2);
        recipe1.getReviews().add(review3);
        
        //Plan -> Recipe (Subscriber chooses recipes in subscription plan)
        plan1.getRecipes().add(recipe1);
        plan2.getRecipes().add(recipe2);
        plan3.getRecipes().add(recipe7);

        //Plan -> Category (Subscriber creates a plan from categories)
        plan1.setCatergory(cat1);
        plan2.setCatergory(cat3);
        plan3.setCatergory(cat5);

        //Plan <- Category (Subscriber creates a plan from categories)
        cat1.getSubscriptionPlans().add(plan1);
        cat3.getSubscriptionPlans().add(plan2);
        cat5.getSubscriptionPlans().add(plan3);

        //Category -> Recipe (Categories have several recipes)
        cat1.getRecipes().add(recipe1);
        cat3.getRecipes().add(recipe2);
        cat5.getRecipes().add(recipe7);

        //Category <- Recipe (Categories have several recipes)
        recipe1.getCategories().add(cat1);
        recipe2.getCategories().add(cat3);
        recipe7.getCategories().add(cat5);

        //Subscriber -> Plan (Subscribers make plans)
        sub1.getSubscriptionPlans().add(plan1);
        sub1.getSubscriptionPlans().add(plan2);
        sub1.getSubscriptionPlans().add(plan3);

        //Subscriber <- Plan (Subscribers make plans)
        plan1.setSubscriber(sub1);
        plan2.setSubscriber(sub1);
        plan3.setSubscriber(sub1);

        //Transaction -> Plan 
        transaction1.setSubscriptionPlan(plan1);
        transaction2.setSubscriptionPlan(plan2);
        transaction3.setSubscriptionPlan(plan3);

        //Transaction <- Plan 
        plan1.setTransaction(transaction1);
        plan2.setTransaction(transaction2);
        plan3.setTransaction(transaction3);
        
        
        Event event1 = new Event("Test 1", new Date(2019-1900, 2, 1, 8, 0), new Date(2019-1900, 2, 1, 9, 0));
        Event event2 = new Event("Test 2", new Date(2019-1900, 2, 2, 8, 0), new Date(2019-1900, 2, 2, 9, 0));
        Event event3 = new Event("Test 3", new Date(2019-1900, 2, 3, 8, 0), new Date(2019-1900, 2, 3, 9, 0));
        eventController.createNewEvent(event1);
        eventController.createNewEvent(event2);
        eventController.createNewEvent(event3);
        staff1.addEvent(event1);
        staff1.addEvent(event2);
        staff1.addEvent(event3);

    }

    public void persist(Object object) {
        em.persist(object);
    }

}
