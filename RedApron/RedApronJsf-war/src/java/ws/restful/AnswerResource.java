/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import entity.Answer;
import exceptions.AnswerNotFoundException;
import exceptions.EnquiryNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import stateless.AnswerControllerLocal;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAnswerRsp;

/**
 * REST Web Service
 *
 * @author MX
 */
@Path("Answer")
public class AnswerResource {

    private AnswerControllerLocal answerControllerLocal;

    @Context
    private UriInfo context;

    public AnswerResource() {
        answerControllerLocal = lookupAnswerControllerLocal();
    }
    
    @Path("retrieveAnswerById/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAnswerById(@PathParam("id") Long id) {
        try
        {
            Answer answer = answerControllerLocal.retrieveAnswerById(id);
            
            RetrieveAnswerRsp retrieveAnswerRsp = new RetrieveAnswerRsp(answer);
            return Response.status(Status.OK).entity(retrieveAnswerRsp).build();
        }
        catch(AnswerNotFoundException ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
        catch (Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveAnswerByEnquiryId/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAnswerByEnquiryId(@PathParam("id") Long id) {
        try
        {
            Answer answer = answerControllerLocal.getAnswerFromEnquiryId(id);
            
            RetrieveAnswerRsp retrieveAnswerRsp = new RetrieveAnswerRsp(answer);
            return Response.status(Status.OK).entity(retrieveAnswerRsp).build();
        }
        catch(EnquiryNotFoundException ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
        catch(AnswerNotFoundException ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
        catch (Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    private AnswerControllerLocal lookupAnswerControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (AnswerControllerLocal) c.lookup("java:global/RedApron/RedApron-ejb/AnswerController!stateless.AnswerControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
