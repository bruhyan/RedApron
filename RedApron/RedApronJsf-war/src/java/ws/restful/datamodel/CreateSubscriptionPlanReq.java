/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Category;
import entity.Subscriber;
import entity.SubscriptionPlan;

/**
 *
 * @author MX
 */
public class CreateSubscriptionPlanReq {
    private SubscriptionPlan subscriptionPlan;
    private Category category;
    private Subscriber subscriber;

    public CreateSubscriptionPlanReq() {
    }

    public CreateSubscriptionPlanReq(SubscriptionPlan subscriptionPlan, Category category, Subscriber subscriber) {
        this.subscriptionPlan = subscriptionPlan;
        this.category = category;
        this.subscriber = subscriber;
    }

    
    /**
     * @return the subscriptionPlan
     */
    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    /**
     * @param subscriptionPlan the subscriptionPlan to set
     */
    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }
    
    
}
