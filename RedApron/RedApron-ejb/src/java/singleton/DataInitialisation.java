/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package singleton;

import entity.Answer;
import entity.Category;
import entity.Enquiry;
import entity.Event;
import entity.Recipe;
import entity.Review;
import entity.Staff;
import entity.Step;
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
import stateless.AnswerControllerLocal;
import stateless.CategoryControllerLocal;
import stateless.EnquiryControllerLocal;
import stateless.EventControllerLocal;
import stateless.RecipeControllerLocal;
import stateless.ReviewControllerLocal;
import stateless.StaffControllerLocal;
import stateless.StepControllerLocal;
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
    private StepControllerLocal stepController;

    @EJB(name = "AnswerControllerLocal")
    private AnswerControllerLocal answerControllerLocal;

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
        staff1.setPicURL("defaultImage.jpeg");
        staff2.setPicURL("defaultImage.jpeg");
        staff3.setPicURL("defaultImage.jpeg");

        staffController.createNewStaff(staff1);
        staffController.createNewStaff(staff2);
        staffController.createNewStaff(staff3);

        Subscriber sub1 = subscriberControllerLocal.createNewSubscriber(new Subscriber("Alpha", "Tan", "alpha@gmail.com", "90000001", "kent ridge", "corner1", 738343, "password"));
        Subscriber sub2 = subscriberControllerLocal.createNewSubscriber(new Subscriber("Kenny", "Tan", "kenny@gmail.com", "90000002", "kent ridge", "corner2", 139345, "password"));
        Subscriber sub3 = subscriberControllerLocal.createNewSubscriber(new Subscriber("Bady", "Tan", "bady@gmail.com", "90000003", "kent ridge", "corner3", 556083, "password"));

        SubscriptionPlan plan1 = subscriptionPlanControllerLocal.createSubscriptionPlan(new SubscriptionPlan(new Date(2019, 4, 1), new Date(2019, 6, 1), "No shrimp i die", 9, 1, SubscriptionPlanStatus.ONGOING, DeliveryDay.MONDAY));
        SubscriptionPlan plan2 = subscriptionPlanControllerLocal.createSubscriptionPlan(new SubscriptionPlan(new Date(2019, 4, 1), new Date(2019, 6, 1), "No shrimp i die", 9, 1, SubscriptionPlanStatus.ONGOING, DeliveryDay.MONDAY));
        SubscriptionPlan plan3 = subscriptionPlanControllerLocal.createSubscriptionPlan(new SubscriptionPlan(new Date(2019, 4, 1), new Date(2019, 6, 1), "No shrimp i die", 9, 1, SubscriptionPlanStatus.ONGOING, DeliveryDay.MONDAY));

        Category cat1 = categoryControllerLocal.createNewCategory(new Category("Seasonal For 2", 40.00, true));
        Category cat2 = categoryControllerLocal.createNewCategory(new Category("Seasonal For 4", 60.00, true));
        Category cat3 = categoryControllerLocal.createNewCategory(new Category("Signature For 2", 40.00, true));
        Category cat4 = categoryControllerLocal.createNewCategory(new Category("Signature For 4", 60.00, true));
        Category cat5 = categoryControllerLocal.createNewCategory(new Category("Vegetarian For 2", 35.00, true));
        Category cat6 = categoryControllerLocal.createNewCategory(new Category("Vegetarian For 4", 52.00, true));
        Category cat7 = categoryControllerLocal.createNewCategory(new Category("Healthy Living For 2", 45.00, true));
        Category cat8 = categoryControllerLocal.createNewCategory(new Category("Healthy Living For 4", 62.00, true));
        Category cat9 = categoryControllerLocal.createNewCategory(new Category("Baking For 2", 45.00, true));
        Category cat10 = categoryControllerLocal.createNewCategory(new Category("Baking For 4", 62.00, true));
        Category cat11 = categoryControllerLocal.createNewCategory(new Category("Quick For 2", 32.00, true));
        Category cat12 = categoryControllerLocal.createNewCategory(new Category("Quick For 4", 52.00, true));

        Recipe recipe1 = recipeControllerLocal.createNewRecipe(new Recipe("Hainan-style Roasted Chicken", "Chicken, Rice, Herbs, Hainan, Cucumber", "with Steamed Oiled Rice & Fresh Cucumbers 30 200", "roastchicken.jpeg", true));
        Recipe recipe2 = recipeControllerLocal.createNewRecipe(new Recipe("Smoky Ancho Baked Chicken", "Chicken, Rice, Spice, Black Beans", "with Spiced Rice & Black Beans 60 300", "bakedchicken.jpg", true)); //860 cal, 35 min
        Recipe recipe3 = recipeControllerLocal.createNewRecipe(new Recipe("Nashville-Style Hot Chicken", "Chicken, Kale, Maple Syrup, Sweet Potato", "with Maple Kale & Mashed Sweet Potatoes 30 810", "chickenkale.jpg", true)); //810 calroies, 
        Recipe recipe4 = recipeControllerLocal.createNewRecipe(new Recipe("Calabrian Shrimp & Orzo", "Shrimp, Zucchini, Orzo", "with Zucchini 50 300", "shrimporzo.jpg", true)); //480 calories, 20 mins
        Recipe recipe5 = recipeControllerLocal.createNewRecipe(new Recipe("Goat Cheese & Mushroom Quesadillas", "Wrap, Mushroom, Goat Cheese, Lemon, Zucchini", "with Lemon-Dressed Zucchini 30 200", "quesadillas.jpg", true)); //vegeterian, 590 cal, 45 mins
        Recipe recipe6 = recipeControllerLocal.createNewRecipe(new Recipe("Middle Eastern-Style Pasta", "Pasata", "with Roasted Broccoli & Brown Butter-Tomato Sauce 70 100", "middleeast.jpeg", true)); //800 cal, 25 mins, vegeterian
        Recipe recipe7 = recipeControllerLocal.createNewRecipe(new Recipe("Vegetable & Freekeh Fried Rice", "Fried RRICEEE", "with Kombu & Peanuts 50 300", "friedrice.jpg", true)); //440 cal, 35 mins, vegeterian
        Recipe recipe8 = recipeControllerLocal.createNewRecipe(new Recipe("Chicken & Curry Mustard", "Curry, Chicken, Coconut milk", "with Carrot & Currant Rice 30 250", "chickencurry.jpg", true)); // 590 cal, 25 mins

        Transaction transaction1 = transactionControllerLocal.createNewTransaction(new Transaction(40.00, new Date(2019 - 1900, 2, 10), PaymentType.MASTER));
        Transaction transaction2 = transactionControllerLocal.createNewTransaction(new Transaction(40.00, new Date(2019 - 1900, 2, 10), PaymentType.VISA));
        Transaction transaction3 = transactionControllerLocal.createNewTransaction(new Transaction(35.00, new Date(2019 - 1900, 2, 10), PaymentType.PAYPAL));
        Transaction transaction4 = transactionControllerLocal.createNewTransaction(new Transaction(50.00, new Date(2019 - 1900, 2, 29), PaymentType.MASTER));
        Transaction transaction5 = transactionControllerLocal.createNewTransaction(new Transaction(40.00, new Date(2019 - 1900, 3, 1), PaymentType.VISA));
        Transaction transaction6 = transactionControllerLocal.createNewTransaction(new Transaction(35.00, new Date(2019 - 1900, 3, 5), PaymentType.PAYPAL));

        Enquiry enquiry1 = enquiryControllerLocal.createNewEnquiry(new Enquiry("Hello, I would like to ask where is my meal?", sub1));
        Enquiry enquiry2 = enquiryControllerLocal.createNewEnquiry(new Enquiry("Give me free food pl0x", sub2));
        Enquiry enquiry3 = enquiryControllerLocal.createNewEnquiry(new Enquiry("Where is the toilet?", sub3));
        Enquiry enquiry4 = enquiryControllerLocal.createNewEnquiry(new Enquiry("How are you?", sub1));

        Review review1 = reviewControllerLocal.createNewReview(new Review("Good, 10/10 would get this again.", 5, new Date(2019 - 1900, 2, 10), sub1, recipe1));
        Review review2 = reviewControllerLocal.createNewReview(new Review("gr8 b8 m8 i r8 8/8", 5, new Date(2019 - 1900, 2, 11), sub1, recipe2));
        Review review3 = reviewControllerLocal.createNewReview(new Review("I have had better", 3, new Date(2019 - 1900, 2, 15), sub3, recipe1));

        Answer answer1 = answerControllerLocal.createNewAnswer(new Answer("We apologise for the inconvenience, your meal's detail will be emailed to you shortly."));

        Step step1 = stepController.createNewStep(new Step("Prepare & roast the potatoes:Preheat the oven to 450°F. Place on a sheet pan. Roast 28 to 30 minutes, or until tender when pierced with a fork.", "roastpotato.png"));
        step1.setOrderNum(1);
        Step step2 = stepController.createNewStep(new Step("Prepare the remaining ingredients:While the potatoes roast, cut off and discard the bottom ½ inch of the broccoli stem; cut the broccoli into small florets.", "prepbroc.png"));
        step2.setOrderNum(2);
        Step step3 = stepController.createNewStep(new Step("Bake the chicken & broccoli:While the potatoes continue to roast, line a separate sheet pan with foil. Pat the chicken dry with paper towels; season and bake.", "bakechic.png"));
        step3.setOrderNum(3);
        Step step4 = stepController.createNewStep(new Step("Finish the broccoli & serve your dish:Evenly top the baked broccoli with the juice of 2 lemon wedges. Serve the baked chicken with finished broccoli and roasted potatoes. Enjoy! ", "sprinkle.png"));
        step4.setOrderNum(4);
        
        Step step5 = stepController.createNewStep(new Step("Cook the rice:In a small pot, combine the rice, a big pinch of salt, and 1 cup of water. Heat to boiling on high. Once boiling, reduce the heat to low. Turn off the heat and fluff with a fork.", "cookrice.png"));
        step5.setOrderNum(1);
        Step step6 = stepController.createNewStep(new Step("Cook & finish the vegetables:While the rice cooks, in a medium pan (nonstick, if you have one), heat 1 teaspoon of olive oil on medium-high until hot. Add the sliced mushrooms in an even layer.", "cookvege.png"));
        step6.setOrderNum(2);
        Step step7 = stepController.createNewStep(new Step("Cook the chicken:While the rice continues to cook, pat the chicken dry with paper towels. Season with salt and pepper on both sides. In the same pan, heat 1 teaspoon of olive oil. ", "panchicken.png", 3));
        step7.setOrderNum(3);
        Step step8 = stepController.createNewStep(new Step("Finish & serve your dish:To the pan of reserved fond, add the sauce (carefully, as the liquid may splatter). Cook on medium-high, stirring frequently. Serve. Enjoy!", "finishserve.png", 4));
        step8.setOrderNum(4);
        
        //setting relation
        recipe2.getSteps().add(step1);
        recipe2.getSteps().add(step2);
        recipe2.getSteps().add(step3);
        recipe2.getSteps().add(step4);
        
        recipe1.getSteps().add(step5);
        recipe1.getSteps().add(step6);
        recipe1.getSteps().add(step7);
        recipe1.getSteps().add(step8);
        
        recipe3.getSteps().add(step5);
        recipe3.getSteps().add(step6);
        recipe3.getSteps().add(step7);
        recipe3.getSteps().add(step8);
        
        recipe4.getSteps().add(step5);
        recipe4.getSteps().add(step6);
        recipe4.getSteps().add(step7);
        recipe4.getSteps().add(step8);
        
        recipe5.getSteps().add(step5);
        recipe5.getSteps().add(step6);
        recipe5.getSteps().add(step7);
        recipe5.getSteps().add(step8);
        
        recipe6.getSteps().add(step5);
        recipe6.getSteps().add(step6);
        recipe6.getSteps().add(step7);
        recipe6.getSteps().add(step8);
        
        recipe7.getSteps().add(step1);
        recipe7.getSteps().add(step2);
        recipe7.getSteps().add(step3);
        recipe7.getSteps().add(step4);
        
        recipe8.getSteps().add(step1);
        recipe8.getSteps().add(step2);
        recipe8.getSteps().add(step3);
        recipe8.getSteps().add(step4);

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

        cat1.getRecipes().add(recipe3);
        cat1.getRecipes().add(recipe4);
        cat1.getRecipes().add(recipe5);
        cat1.getRecipes().add(recipe6);
        cat1.getRecipes().add(recipe2);
        cat1.getRecipes().add(recipe7);
        cat1.getRecipes().add(recipe8);
        
        cat2.getRecipes().add(recipe3);
        cat2.getRecipes().add(recipe4);
        cat2.getRecipes().add(recipe5);
        cat2.getRecipes().add(recipe6);
        
        cat3.getRecipes().add(recipe1);
        cat3.getRecipes().add(recipe7);
        cat3.getRecipes().add(recipe3);
        cat3.getRecipes().add(recipe8);
        
        cat4.getRecipes().add(recipe1);
        cat4.getRecipes().add(recipe2);
        cat4.getRecipes().add(recipe3);
        
        cat5.getRecipes().add(recipe4);
        cat5.getRecipes().add(recipe5);
        cat5.getRecipes().add(recipe6);
        
        cat6.getRecipes().add(recipe7);
        cat6.getRecipes().add(recipe8);
        cat6.getRecipes().add(recipe1);
        
        cat7.getRecipes().add(recipe1);
        cat7.getRecipes().add(recipe2);
        cat7.getRecipes().add(recipe3);
        
        cat8.getRecipes().add(recipe4);
        cat8.getRecipes().add(recipe5);
        cat8.getRecipes().add(recipe6);
        
        cat9.getRecipes().add(recipe7);
        cat9.getRecipes().add(recipe8);
        cat9.getRecipes().add(recipe1);
        
        cat10.getRecipes().add(recipe1);
        cat10.getRecipes().add(recipe2);
        cat10.getRecipes().add(recipe3);
        
        cat11.getRecipes().add(recipe4);
        cat11.getRecipes().add(recipe6);
        cat11.getRecipes().add(recipe2);
        
        cat12.getRecipes().add(recipe7);
        cat12.getRecipes().add(recipe6);
        cat12.getRecipes().add(recipe2);
      

        //Category <- Recipe (Categories have several recipes)
        recipe1.getCategories().add(cat1);
        recipe2.getCategories().add(cat3);
        recipe7.getCategories().add(cat5);

        recipe2.getCategories().add(cat1);
        recipe3.getCategories().add(cat1);
        recipe4.getCategories().add(cat1);
        recipe5.getCategories().add(cat1);
        recipe6.getCategories().add(cat1);
        recipe7.getCategories().add(cat1);
        recipe8.getCategories().add(cat1);
        
        recipe3.getCategories().add(cat2);
        recipe4.getCategories().add(cat2);
        recipe5.getCategories().add(cat2);
        recipe6.getCategories().add(cat2);
        
        recipe1.getCategories().add(cat3);
        recipe7.getCategories().add(cat3);
        recipe3.getCategories().add(cat3);
        recipe8.getCategories().add(cat3);
        
        recipe1.getCategories().add(cat4);
        recipe2.getCategories().add(cat4);
        recipe3.getCategories().add(cat4);
        
        recipe4.getCategories().add(cat5);
        recipe5.getCategories().add(cat5);
        recipe6.getCategories().add(cat5);
        
        recipe7.getCategories().add(cat6);
        recipe8.getCategories().add(cat6);
        recipe1.getCategories().add(cat6);
        
        recipe1.getCategories().add(cat7);
        recipe2.getCategories().add(cat7);
        recipe3.getCategories().add(cat7);
        
        recipe4.getCategories().add(cat8);
        recipe5.getCategories().add(cat8);
        recipe6.getCategories().add(cat8);
        
        recipe7.getCategories().add(cat9);
        recipe8.getCategories().add(cat9);
        recipe1.getCategories().add(cat9);
        
        recipe1.getCategories().add(cat10);
        recipe2.getCategories().add(cat10);
        recipe3.getCategories().add(cat10);
        
        recipe4.getCategories().add(cat11);
        recipe6.getCategories().add(cat11);
        recipe2.getCategories().add(cat11);
        
        recipe7.getCategories().add(cat12);
        recipe6.getCategories().add(cat12);
        recipe2.getCategories().add(cat12);
        
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

        enquiry1.setAnswer(answer1);

        answer1.setEnquiry(enquiry1);
        answer1.setStaff(staff1);

        Event event1 = new Event("Test 1", new Date(2019 - 1900, 2, 1, 8, 0), new Date(2019 - 1900, 2, 1, 9, 0));
        Event event2 = new Event("Test 2", new Date(2019 - 1900, 2, 2, 8, 0), new Date(2019 - 1900, 2, 2, 9, 0));
        Event event3 = new Event("Test 3", new Date(2019 - 1900, 2, 3, 8, 0), new Date(2019 - 1900, 2, 3, 9, 0));
        eventController.createNewEvent(event1);
        eventController.createNewEvent(event2);
        eventController.createNewEvent(event3);
        staff1.addEvent(event1);
        staff1.addEvent(event2);
        staff1.addEvent(event3);

        staff1.getAnswers().add(answer1);

    }

    public void persist(Object object) {
        em.persist(object);
    }

    public void persist1(Object object) {
        em.persist(object);
    }

}
