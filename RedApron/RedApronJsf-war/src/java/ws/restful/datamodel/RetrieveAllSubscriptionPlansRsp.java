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
 * @author MX
 */
public class RetrieveAllSubscriptionPlansRsp {
    private List<SubscriptionPlan> subscriptionPlanEntities;

    public RetrieveAllSubscriptionPlansRsp() {
    }

    public RetrieveAllSubscriptionPlansRsp(List<SubscriptionPlan> subscriptionPlanEntities) {
        this.subscriptionPlanEntities = subscriptionPlanEntities;
    }

    /**
     * @return the subscriptionPlanEntities
     */
    public List<SubscriptionPlan> getSubscriptionPlanEntities() {
        return subscriptionPlanEntities;
    }

    /**
     * @param subscriptionPlanEntities the subscriptionPlanEntities to set
     */
    public void setSubscriptionPlanEntities(List<SubscriptionPlan> subscriptionPlanEntities) {
        this.subscriptionPlanEntities = subscriptionPlanEntities;
    }
    
    
}
