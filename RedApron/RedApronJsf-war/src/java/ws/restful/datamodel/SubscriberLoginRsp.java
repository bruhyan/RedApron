/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Subscriber;

/**
 *
 * @author MX
 */
public class SubscriberLoginRsp {
    
    private Subscriber subscriber;

    public SubscriberLoginRsp(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public SubscriberLoginRsp() {
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }
    
    
}
