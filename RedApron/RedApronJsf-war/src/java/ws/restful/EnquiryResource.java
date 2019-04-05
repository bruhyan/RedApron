/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import entity.Enquiry;
import exceptions.EnquiryNotFoundException;
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
import stateless.EnquiryControllerLocal;
import ws.restful.datamodel.CreateEnquiryReq;
import ws.restful.datamodel.CreateEnquiryRsp;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAllEnquiriesRsp;
import ws.restful.datamodel.RetrieveEnquiryRsp;
import ws.restful.datamodel.UpdateEnquiryReq;

/**
 * REST Web Service
 *
 * @author MX
 */
@Path("Enquiry")
public class EnquiryResource {

    private EnquiryControllerLocal enquiryControllerLocal;

    @Context
    private UriInfo context;

    public EnquiryResource() {
        enquiryControllerLocal = lookupEnquiryControllerLocal();
    }

    @Path("retrieveEnquiryById/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveEnquiryById(@PathParam("id") Long id) {
        try
        {
            Enquiry enquiry = enquiryControllerLocal.retrieveEnquiryById(id);
            
            System.out.println("enquiry: " + enquiry);
            //detach the two way bidirectional relationship
            enquiry.setAnswer(null);
            enquiry.setSubscriber(null);
            
            RetrieveEnquiryRsp retrieveEnquiryRsp = new RetrieveEnquiryRsp(enquiry);
            return Response.status(Status.OK).entity(retrieveEnquiryRsp).build();
        }
        catch(EnquiryNotFoundException ex){
            ex.printStackTrace();
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
        catch (Exception ex){
            ex.printStackTrace();
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveAllEnquiries")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllEnquiries(){
        try{
            List <Enquiry> enquiryEntities = enquiryControllerLocal.retrieveAllEnquiries();
            
            for(Enquiry e: enquiryEntities)
            {
               e.setAnswer(null);
               e.setSubscriber(null);
            }
            
            RetrieveAllEnquiriesRsp retrieveAllSubscribersRsp = new RetrieveAllEnquiriesRsp(enquiryEntities);
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
    public Response createEnquiry(CreateEnquiryReq createEnquiryReq){
        if(createEnquiryReq != null){
            try{
                Enquiry enquiry = enquiryControllerLocal.createNewEnquiry(createEnquiryReq.getEnquiry());
                CreateEnquiryRsp createEnquiryRsp = new CreateEnquiryRsp(enquiry);
                return Response.status(Status.OK).entity(createEnquiryRsp).build();
            }
            catch(Exception ex){
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create enquiry request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateEnquiry(UpdateEnquiryReq updateEnquiryReq){
        if (updateEnquiryReq != null){
            try{
                enquiryControllerLocal.updateEnquiry(updateEnquiryReq.getEnquiry());
                return Response.status(Status.OK).build();
            }
            catch(EnquiryNotFoundException ex){
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
            ErrorRsp errorRsp = new ErrorRsp("Invalid update enquiry request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteEnquiry(@QueryParam("id") Long id){
        try{
            enquiryControllerLocal.deleteEnquiry(id);
            return Response.status(Status.OK).build();
        }
        catch(EnquiryNotFoundException ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
        catch(Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    private EnquiryControllerLocal lookupEnquiryControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (EnquiryControllerLocal) c.lookup("java:global/RedApron/RedApron-ejb/EnquiryController!stateless.EnquiryControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
