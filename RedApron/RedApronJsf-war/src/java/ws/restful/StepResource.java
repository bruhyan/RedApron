/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import entity.Step;
import exceptions.StepNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import stateless.StepControllerLocal;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAllStepsRsp;
import ws.restful.datamodel.RetrieveStepRsp;

/**
 * REST Web Service
 *
 * @author MX
 */
@Path("Step")
public class StepResource {

    private StepControllerLocal stepControllerLocal ;

    @Context
    private UriInfo context;

    public StepResource() {
        stepControllerLocal = lookupStepControllerLocal();
    }
    
    @Path("retrieveStepById/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSubscriberById(@PathParam("id") Long id) {
        try
        {
            Step step = stepControllerLocal.retrieveStepById(id);
            
            //detach the two way bidirectional relationship
            
            RetrieveStepRsp retrieveStepRsp = new RetrieveStepRsp(step);
            return Response.status(Status.OK).entity(retrieveStepRsp).build();
        }
        catch(StepNotFoundException ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
        catch (Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveAllSteps")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllSteps(){
        try{
            List <Step> stepEntities = stepControllerLocal.retrieveAllSteps();
            
            for(Step step : stepEntities)
            {
                
            }
            
            RetrieveAllStepsRsp retrieveAllStepsRsp = new RetrieveAllStepsRsp(stepEntities);
            return Response.status(Status.OK).entity(retrieveAllStepsRsp).build();
        }
        catch(Exception ex){
            ex.printStackTrace();
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    private StepControllerLocal lookupStepControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (StepControllerLocal) c.lookup("java:global/RedApron/RedApron-ejb/StepController!stateless.StepControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    

}
