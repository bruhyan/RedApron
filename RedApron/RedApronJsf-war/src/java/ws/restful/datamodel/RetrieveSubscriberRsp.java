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
public class RetrieveSubscriberRsp {
    
    private Subscriber subscriber;

    public RetrieveSubscriberRsp() {
    }

    public RetrieveSubscriberRsp(Subscriber subscriber) {
        this.subscriber = subscriber;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }
    
    
}
