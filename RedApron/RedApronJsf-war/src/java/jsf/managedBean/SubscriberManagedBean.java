/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Subscriber;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import stateless.SubscriberControllerLocal;

/**
 *
 * @author matthealoo
 */
@Named(value = "subscriberManagedBean")
@RequestScoped
public class SubscriberManagedBean {

    @EJB
    private SubscriberControllerLocal subscriberController;
    
    private List<Subscriber> subscriberEntities;
    private List<Subscriber> filteredSubscriberEntities;

    /**
     * Creates a new instance of SubscriberManagedBean
     */
    public SubscriberManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        subscriberEntities = subscriberController.retrieveAllSubscribers();
    }

    public List<Subscriber> getSubscriberEntities() {
        return subscriberEntities;
    }

    public void setSubscriberEntities(List<Subscriber> subscriberEntities) {
        this.subscriberEntities = subscriberEntities;
    }

    public List<Subscriber> getFilteredSubscriberEntities() {
        return filteredSubscriberEntities;
    }

    public void setFilteredSubscriberEntities(List<Subscriber> filteredSubscriberEntities) {
        this.filteredSubscriberEntities = filteredSubscriberEntities;
    }

    
}
