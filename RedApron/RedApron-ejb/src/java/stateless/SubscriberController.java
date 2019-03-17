/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Subscriber;
import exceptions.SubscriberNotFoundException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author matthealoo
 */
@Stateless
public class SubscriberController implements SubscriberControllerLocal {

    @PersistenceContext(unitName = "RedApron-ejbPU")
    private EntityManager em;

    public SubscriberController() {
    }

    @Override
    public Long createNewSubscriber(Subscriber newSubscriber) {
        //check if subscriber already exist
        //if not then persist
        
        em.persist(newSubscriber);
        em.flush();
        return newSubscriber.getSubscriberId();
    }
    
    @Override
    public List<Subscriber> retrieveAllSubscribers() {
        Query query = em.createQuery("SELECT s FROM Subscriber s ORDER BY s.subscriberId ");
        List<Subscriber> subscribers = query.getResultList();
        
        for(Subscriber subs:subscribers) {
            subs.getReviews().size();
            subs.getSubscriptionPlans().size();
        }
        
        return subscribers;
    }
    
    @Override
    public Subscriber retrieveSubscriberById(Long subscriberId) throws SubscriberNotFoundException { 
        if(subscriberId == null) {
            throw new SubscriberNotFoundException("Subscriber ID not provided");
        }
        Subscriber subscriber = em.find(Subscriber.class, subscriberId);
        if (subscriber != null) {
            subscriber.getReviews().size();
            subscriber.getSubscriptionPlans().size();
            return subscriber;
        }else {
            throw new SubscriberNotFoundException("Subscriber ID " + subscriberId + " does not exist!");
        }  
    }
    
    @Override
    public Subscriber retrieveSubscriberByEmail(String subscriberEmail) throws SubscriberNotFoundException {
        if(subscriberEmail == null) {
            throw new SubscriberNotFoundException("Subscriber ID not provided");
        }
        Query query = em.createQuery("SELECT s FROM Subscriber s WHERE s.email = :subscriberEmail");
        Subscriber subscriber = (Subscriber)query.getSingleResult();
        if (subscriber != null) {
            subscriber.getReviews().size();
            subscriber.getSubscriptionPlans().size();
            return subscriber;
        }else {
            throw new SubscriberNotFoundException("Subscriber ID " + subscriberEmail + " does not exist!");
        } 
    }
    
    @Override
    public void deleteSubscriber(Long subscriberId) throws SubscriberNotFoundException {
        Subscriber subscriberToDelete = retrieveSubscriberById(subscriberId);
        // need to do 2 way relationship chibaboom
        //not complete
        
        em.remove(subscriberToDelete);
        
    }
    
    
    
}
