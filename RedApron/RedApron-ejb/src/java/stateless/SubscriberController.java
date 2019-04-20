/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Subscriber;
import entity.SubscriptionPlan;
import exceptions.InvalidLoginCredentialException;
import exceptions.SubscriberNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.security.CryptographicHelper;

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
    public Subscriber createNewSubscriber(Subscriber newSubscriber){
        //check if subscriber already exist
        //if not then persist
        newSubscriber.hashPassword();
        em.persist(newSubscriber);
        em.flush();
        return newSubscriber;
    }
    
    @Override
    public Subscriber subscriberLogin(String email, String password) throws InvalidLoginCredentialException{
        try{
            Subscriber subscriber = retrieveSubscriberByEmail(email);
            String passwordHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + subscriber.getSalt()));
            
            System.out.println("subscriber pw hash "+subscriber.getPassword());
            System.out.println("incoming pw hash "+passwordHash);
            
            if(subscriber.getPassword().equals(passwordHash))
            {   
                System.out.println("password hash not equal");
                subscriber.getEnquiries().clear();
                subscriber.getSubscriptionPlans().clear();
                subscriber.getReviews().clear();
                return subscriber;
            }
            else
            {
                System.out.println("invalid login credential");
                throw new InvalidLoginCredentialException("Email does not exist or invalid password!");
            }
            
        } catch (SubscriberNotFoundException ex) {
            System.out.println("subscriber not found");
            throw new InvalidLoginCredentialException("Email does not exist or invalid password!");
        }
    }
    
    @Override
    public List<Subscriber> retrieveAllSubscribers() {
        Query query = em.createQuery("SELECT s FROM Subscriber s ORDER BY s.subscriberId");
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
        
        System.out.println("email: " + subscriberEmail);
        
        Query query = em.createQuery("SELECT s FROM Subscriber s WHERE s.email =:insubscriberEmail");
        query.setParameter("insubscriberEmail", subscriberEmail);
        
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
    
    
    
    @Override
    public void updateSubscriber(Subscriber subscriber) throws SubscriberNotFoundException{
        
        Subscriber subscriberToUpdate = retrieveSubscriberById(subscriber.getSubscriberId());
        System.out.println("test: "+CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing("password" + subscriberToUpdate.getSalt())));
        System.out.println("Before: "+subscriberToUpdate.getPassword()+ " "+ subscriberToUpdate.getSalt());
        System.out.println("Before password raw: "+subscriber.getPassword());
        if(subscriberToUpdate != null){
            subscriberToUpdate.setFirstName(subscriber.getFirstName());
            subscriberToUpdate.setLastName(subscriber.getLastName());
            subscriberToUpdate.setAddressLine1(subscriber.getAddressLine1());
            subscriberToUpdate.setAddressLine2(subscriber.getAddressLine2());
            subscriberToUpdate.setEmail(subscriber.getEmail());
            subscriberToUpdate.setEnquiries(subscriber.getEnquiries());
            subscriberToUpdate.setPassword(subscriber.getPassword());
            subscriberToUpdate.hashPassword();
            subscriberToUpdate.setPhoneNumber(subscriber.getPhoneNumber());
            subscriberToUpdate.setPostalCode(subscriber.getPostalCode());
            subscriberToUpdate.setReviews(subscriber.getReviews());
            subscriberToUpdate.setSubscriptionPlans(subscriber.getSubscriptionPlans());
            
            em.merge(subscriberToUpdate);
            System.out.println("After: "+subscriberToUpdate.getPassword()+ " "+ subscriberToUpdate.getSalt());
        }
    }
       
    public void addPlanToSubscriber(Subscriber sub, SubscriptionPlan plan) {
        
    }
    
    
    
}
