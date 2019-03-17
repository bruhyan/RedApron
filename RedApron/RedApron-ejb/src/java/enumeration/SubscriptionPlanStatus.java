/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumeration;

/**
 *
 * @author mdk12
 */
public enum SubscriptionPlanStatus {
    ONGOING("Ongoing"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");
    
    private String label;
    
    private SubscriptionPlanStatus(String label){
        this.label = label;
    }
    
    public String getLabel(){
        return label;
    }
}
