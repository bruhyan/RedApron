/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import entity.SubscriptionPlan;
import exceptions.SubscriptionPlanNotFoundException;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import stateless.SubscriptionPlanControllerLocal;
import ws.restful.datamodel.CreateSubscriptionPlanReq;
import ws.restful.datamodel.CreateSubscriptionPlanRsp;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAllSubscriptionPlansRsp;
import ws.restful.datamodel.UpdateSubscriptionPlanReq;

/**
 * REST Web Service
 *
 * @author MX
 */
@Path("SubscriptionPlan")
public class SubscriptionPlanResource {

    private SubscriptionPlanControllerLocal subscriptionPlanControllerLocal;

    @Context
    private UriInfo context;

    public SubscriptionPlanResource() {
        subscriptionPlanControllerLocal = lookupSubscriptionPlanControllerLocal();
    }
    
    @Path("retrieveAllSubscriptionPlans")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllSubscriptionPlans(){
        try{
            List <SubscriptionPlan> subscriptionPlanEntities = subscriptionPlanControllerLocal.retrieveAllSubscriptionPlans();
            
            for(SubscriptionPlan s : subscriptionPlanEntities)
            {
                s.setCatergory(null);
                s.setRecipes(null);
                s.setTransaction(null);
                s.setSubscriber(null);
                s.setRecipes(null);
            }
            
            RetrieveAllSubscriptionPlansRsp retrieveAllSubscriptionPlansRsp = new RetrieveAllSubscriptionPlansRsp(subscriptionPlanEntities);
            return Response.status(Status.OK).entity(retrieveAllSubscriptionPlansRsp).build();
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
    public Response createSubscriptionPlan(CreateSubscriptionPlanReq createSubscriptionPlanReq){
        if(createSubscriptionPlanReq != null){
            try{
                SubscriptionPlan subscriptionPlan = subscriptionPlanControllerLocal.createSubscriptionPlan(createSubscriptionPlanReq.getSubscriptionPlan());
                CreateSubscriptionPlanRsp createSubscriberRsp = new CreateSubscriptionPlanRsp(subscriptionPlan);
                return Response.status(Status.OK).entity(createSubscriberRsp).build();
            }
            catch(Exception ex){
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create subscription plan request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSubscriptionPlan(UpdateSubscriptionPlanReq updateSubscriptionPlanReq){
        if (updateSubscriptionPlanReq != null){
            try{
                subscriptionPlanControllerLocal.updatePlan(updateSubscriptionPlanReq.getSubscriptionPlan(), 
                        updateSubscriptionPlanReq.getSubscriptionPlan().getCatergory().getCategoryId(), 
                        updateSubscriptionPlanReq.getSubscriptionPlan().getSubscriber().getSubscriberId());
                return Response.status(Status.OK).build();
            }
            catch(Exception ex){
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }
        else
        {
            ErrorRsp errorRsp = new ErrorRsp("Invalid update Subscription Plan request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSubscriptionPlan(@QueryParam("id") Long id){
        try{
            subscriptionPlanControllerLocal.deleteSubscriptionPlan(id);
            return Response.status(Status.OK).build();
        }
        catch(SubscriptionPlanNotFoundException ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
        catch(Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    private SubscriptionPlanControllerLocal lookupSubscriptionPlanControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (SubscriptionPlanControllerLocal) c.lookup("java:global/RedApron/RedApron-ejb/SubscriptionPlanController!stateless.SubscriptionPlanControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    
}
