/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.SubscriptionPlan;

/**
 *
 * @author MX
 */
public class CreateSubscriptionPlanRsp {
    private SubscriptionPlan subscriptionPlan;

    public CreateSubscriptionPlanRsp() {
    }

    public CreateSubscriptionPlanRsp(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
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
    
    
}
