/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.SubscriptionPlan;
import java.util.List;

/**
 *
 * @author Mdk12
 */
public class RetrieveLatestSubscriptionPlanRsp {
    private List<SubscriptionPlan> subscriptionPlans;

    public RetrieveLatestSubscriptionPlanRsp() {
    }

    public RetrieveLatestSubscriptionPlanRsp(List<SubscriptionPlan>  subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
    }

    /**
     * @return the subscriptionPlanEntities
     */
    public List<SubscriptionPlan> getSubscriptionPlan() {
        return subscriptionPlans;
    }

    /**
     * @param subscriptionPlanEntities the subscriptionPlanEntities to set
     */
    public void setSubscriptionPlan(List<SubscriptionPlan>  subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
    }
    
    
}
