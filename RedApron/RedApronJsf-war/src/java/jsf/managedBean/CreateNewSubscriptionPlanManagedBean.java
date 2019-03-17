/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.SubscriptionPlan;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import stateless.SubscriptionPlanControllerLocal;
import enumeration.SubscriptionPlanStatus;
import enumeration.DeliveryDay;

/**
 *
 * @author mdk12
 */
@Named(value = "createNewSubscriptionPlanManagedBean")
@RequestScoped
public class CreateNewSubscriptionPlanManagedBean {

    @EJB(name = "SubscriptionPlanControllerLocal")
    private SubscriptionPlanControllerLocal subscriptionPlanControllerLocal;
    
    private SubscriptionPlan subscriptionPlan;
    private SubscriptionPlanStatus mySubscriptionPlanStatusEnum;


    
    public CreateNewSubscriptionPlanManagedBean() {
        subscriptionPlan = new SubscriptionPlan();
    }
    
    public void createNewSubscriptionPlan(){
        Long subscriptionPlanId = subscriptionPlanControllerLocal.createSubscriptionPlan(subscriptionPlan);
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "New subscription plan made with ID " + subscriptionPlanId));
        
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }
    
    public SubscriptionPlanStatus[] subscriptionPlanStatus(){
        return SubscriptionPlanStatus.values();
    }
    
    public DeliveryDay[] deliveryDays(){
        return DeliveryDay.values();
    }
    
    
}
