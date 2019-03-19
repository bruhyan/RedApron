/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Subscriber;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import stateless.SubscriberControllerLocal;

/**
 *
 * @author Bryan
 */
@Named(value = "createNewSubscriberManagedBean")
@RequestScoped
public class CreateNewSubscriberManagedBean {

    @EJB
    private SubscriberControllerLocal subscriberControllerLocal;

    private Subscriber newSubscriber; //model
    
    
    public CreateNewSubscriberManagedBean() {
        newSubscriber = new Subscriber();
    }
    
    public void createNewSubscriber() {
        newSubscriber = subscriberControllerLocal.createNewSubscriber(newSubscriber);
        Long newSubscriberId = newSubscriber.getSubscriberId();
    
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New subscriber created successfully: " + newSubscriberId,"New subscriber created successfully: " + newSubscriberId ));
    }

    public Subscriber getNewSubscriber() {
        return newSubscriber;
    }

    public void setNewSubscriber(Subscriber newSubscriber) {
        this.newSubscriber = newSubscriber;
    }
    
}
