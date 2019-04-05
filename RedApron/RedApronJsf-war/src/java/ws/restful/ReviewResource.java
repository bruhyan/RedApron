/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import entity.Review;
import exceptions.ReviewNotFoundException;
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
import stateless.ReviewControllerLocal;
import ws.restful.datamodel.CreateReviewReq;
import ws.restful.datamodel.CreateReviewRsp;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAllReviewsRsp;
import ws.restful.datamodel.RetrieveReviewRsp;
import ws.restful.datamodel.UpdateReviewReq;

/**
 * REST Web Service
 *
 * @author MX
 */
@Path("Review")
public class ReviewResource {

    private ReviewControllerLocal reviewControllerLocal;

    @Context
    private UriInfo context;

    public ReviewResource() {
        reviewControllerLocal = lookupReviewControllerLocal();
    }

    @Path("retrieveReviewById/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveReviewById(@PathParam("id") Long id) {
        try
        {
            Review review = reviewControllerLocal.retrieveReviewById(id);
            
            //detach the two way bidirectional relationship
            review.setRecipe(null);
            review.setSubscriber(null);
            
            RetrieveReviewRsp retrieveReviewRsp = new RetrieveReviewRsp(review);
            return Response.status(Status.OK).entity(retrieveReviewRsp).build();
        }
        catch(ReviewNotFoundException ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
        catch (Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveAllReviews")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllReviews(){
        try{
            List <Review> reviewEntities = reviewControllerLocal.retrieveAllReviews();
            
            for(Review review : reviewEntities)
            {
                review.setRecipe(null);
                review.setSubscriber(null);
            }
            
            RetrieveAllReviewsRsp retrieveAllReviewsRsp = new RetrieveAllReviewsRsp(reviewEntities);
            return Response.status(Status.OK).entity(retrieveAllReviewsRsp).build();
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
    public Response createReview(CreateReviewReq createReviewReq){
        if(createReviewReq != null){
            try{
                Review review = reviewControllerLocal.createNewReview(createReviewReq.getReview());
                CreateReviewRsp createReviewRsp = new CreateReviewRsp(review);
                return Response.status(Status.OK).entity(createReviewRsp).build();
            }
            catch(Exception ex){
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create review request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateReview(UpdateReviewReq updateReviewReq){
        if (updateReviewReq != null){
            try{
                reviewControllerLocal.updateReview(updateReviewReq.getReview());
                return Response.status(Status.OK).build();
            }
            catch(ReviewNotFoundException ex){
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
            ErrorRsp errorRsp = new ErrorRsp("Invalid update review request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteReview(@QueryParam("id") Long id){
        try{
            reviewControllerLocal.deleteReview(id);
            return Response.status(Status.OK).build();
        }
        catch(ReviewNotFoundException ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
        catch(Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    private ReviewControllerLocal lookupReviewControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (ReviewControllerLocal) c.lookup("java:global/RedApron/RedApron-ejb/ReviewController!stateless.ReviewControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
