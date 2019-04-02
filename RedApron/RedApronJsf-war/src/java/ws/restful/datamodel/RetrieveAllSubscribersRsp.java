/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Subscriber;
import java.util.List;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author MX
 */
@XmlType(name = "retrieveAllSubscribersRsp", propOrder = {
    "subscriberEntities"
})
public class RetrieveAllSubscribersRsp {
    
    private List <Subscriber> subscriberEntities;

    public RetrieveAllSubscribersRsp() {
    }

    public RetrieveAllSubscribersRsp(List<Subscriber> subscriberEntities) {
        this.subscriberEntities = subscriberEntities;
    }

    public List <Subscriber> getSubscriberEntities() {
        return subscriberEntities;
    }

    public void setSubscriberEntities(List <Subscriber> subscriberEntities) {
        this.subscriberEntities = subscriberEntities;
    }
    
    
}
