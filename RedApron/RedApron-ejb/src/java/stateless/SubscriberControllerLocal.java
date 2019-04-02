/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Subscriber;
import exceptions.SubscriberNotFoundException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author matthealoo
 */
@Local
public interface SubscriberControllerLocal {

    public Subscriber createNewSubscriber(Subscriber newSubscriber);

    public List<Subscriber> retrieveAllSubscribers();

    public Subscriber retrieveSubscriberById(Long subscriberId) throws SubscriberNotFoundException;

    public Subscriber retrieveSubscriberByEmail(String subscriberEmail) throws SubscriberNotFoundException;

    public void deleteSubscriber(Long subscriberId) throws SubscriberNotFoundException;

    public void updateSubscriber(Subscriber subscriber) throws SubscriberNotFoundException;
    
}
