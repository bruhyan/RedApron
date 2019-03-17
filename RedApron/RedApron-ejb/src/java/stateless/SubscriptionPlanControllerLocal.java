/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.SubscriptionPlan;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author matthealoo
 */
@Local
public interface SubscriptionPlanControllerLocal {

    public SubscriptionPlan createSubscriptionPlan(SubscriptionPlan subscriptionPlan);

    public List<SubscriptionPlan> retrieveAllSubscriptionPlans();
    
}
