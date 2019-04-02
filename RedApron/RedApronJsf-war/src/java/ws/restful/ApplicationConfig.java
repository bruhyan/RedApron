/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author MX
 */
@javax.ws.rs.ApplicationPath("Resources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        resources.add(EnquiryResource.class);
        resources.add(AnswerResource.class);
        resources.add(CategoryResource.class);
        resources.add(RecipeResource.class);
        resources.add(SubscriberResource.class);
        resources.add(ReviewResource.class);
        resources.add(SubscriptionPlanResource.class);
        resources.add(StepResource.class);
//        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
//    private void addRestResourceClasses(Set<Class<?>> resources) {
//        resources.add(ws.restful.SubscriberResource.class);
//    }
    
}
