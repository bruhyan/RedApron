/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import entity.Subscriber;
import exceptions.CreateSubscriberException;
import exceptions.SubscriberNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import stateless.SubscriberControllerLocal;
import ws.restful.datamodel.CreateSubscriberReq;
import ws.restful.datamodel.CreateSubscriberRsp;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAllSubscribersRsp;
import ws.restful.datamodel.RetrieveSubscriberRsp;
import ws.restful.datamodel.UpdateSubscriberReq;

/**
 * REST Web Service
 *
 * @author MX
 */
@Path("Subscriber")
public class SubscriberResource {

    @Context
    private UriInfo context;
    
    private SubscriberControllerLocal subscriberControllerLocal;

    public SubscriberResource() {
        subscriberControllerLocal = lookupSubscriberControllerLocal();
    }

    @Path("retrieveSubscriberById/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSubscriberById(@PathParam("id") Long id) {
        try
        {
            Subscriber subscriber = subscriberControllerLocal.retrieveSubscriberById(id);
            
            //detach the two way bidirectional relationship
            subscriber.getEnquiries().clear();
            subscriber.getSubscriptionPlans().clear();
            subscriber.getReviews().clear();
            
            RetrieveSubscriberRsp retrieveSubscriberRsp = new RetrieveSubscriberRsp(subscriber);
            return Response.status(Status.OK).entity(retrieveSubscriberRsp).build();
        }
        catch(SubscriberNotFoundException ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
        catch (Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveAllSubscribers")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllSubscribers(){
        try{
            List <Subscriber> subscriberEntities = subscriberControllerLocal.retrieveAllSubscribers();
            
            for(Subscriber subscriber:subscriberEntities)
            {
                subscriber.getEnquiries().clear();
                subscriber.getReviews().clear();
                subscriber.getSubscriptionPlans().clear();
            }
            
            RetrieveAllSubscribersRsp retrieveAllSubscribersRsp = new RetrieveAllSubscribersRsp(subscriberEntities);
            return Response.status(Status.OK).entity(retrieveAllSubscribersRsp).build();
        }
        catch(Exception ex){
            ex.printStackTrace();
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSubscriber(CreateSubscriberReq createSubscriberReq){
        if(createSubscriberReq != null){
            try{
                Subscriber subscriber = subscriberControllerLocal.createNewSubscriber(createSubscriberReq.getSubscriber());
                CreateSubscriberRsp createSubscriberRsp = new CreateSubscriberRsp(subscriber);
                return Response.status(Status.OK).entity(createSubscriberRsp).build();
            }
            catch(Exception ex){
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create subscriber request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSubscriber(UpdateSubscriberReq updateSubscriberReq){
        if (updateSubscriberReq != null){
            try{
                subscriberControllerLocal.updateSubscriber(updateSubscriberReq.getSubscriber());
                return Response.status(Status.OK).build();
            }
            catch(SubscriberNotFoundException ex){
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();  
            }
            catch(Exception ex){
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update subscriber request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSubscriber(@QueryParam("id") Long id){
        try{
            subscriberControllerLocal.deleteSubscriber(id);
            return Response.status(Status.OK).build();
        }
        catch(SubscriberNotFoundException ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
        catch(Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    private SubscriberControllerLocal lookupSubscriberControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (SubscriberControllerLocal) c.lookup("java:global/RedApron/RedApron-ejb/SubscriberController!stateless.SubscriberControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
