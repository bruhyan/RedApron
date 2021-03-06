/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.SubscriptionPlan;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import stateless.SubscriptionPlanControllerLocal;

/**
 *
 * @author mdk12
 */
@Named(value = "viewAllSubscriptionPlanManagedBean")
@ViewScoped
public class ViewAllSubscriptionPlanManagedBean implements Serializable{

    @EJB(name = "SubscriptionPlanControllerLocal")
    private SubscriptionPlanControllerLocal subscriptionPlanControllerLocal;

    private List<SubscriptionPlan> subscriptionPlans;

    public ViewAllSubscriptionPlanManagedBean() {
        subscriptionPlans = new ArrayList();
    }

    @PostConstruct
    public void postConstruct() {
        subscriptionPlans = subscriptionPlanControllerLocal.retrieveAllSubscriptionPlans();
    }

    public List<SubscriptionPlan> getSubscriptionPlans() {
        return subscriptionPlans;
    }

    public void setSubscriptionPlans(List<SubscriptionPlan> subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
    }

}
