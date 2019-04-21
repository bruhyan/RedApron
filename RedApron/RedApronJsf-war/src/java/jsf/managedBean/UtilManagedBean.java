/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import enumeration.DeliveryDay;
import enumeration.SubscriptionPlanStatus;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Bryan
 */
@Named(value = "utilManagedBean")
@ViewScoped
public class UtilManagedBean implements Serializable {

    /**
     * Creates a new instance of UtilManagedBean
     */
    private SubscriptionPlanStatus subscriptionPlans;
    public UtilManagedBean() {
    }
    
    @PostConstruct
    public void init(){
        
    }
    
    public SubscriptionPlanStatus[] getPlanStatusValues() {
        return SubscriptionPlanStatus.values();
    }
    
    public DeliveryDay[] getDeliveryDayValues() {
        return DeliveryDay.values();
    }
}
