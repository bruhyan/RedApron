/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import entity.Category;
import exceptions.CategoryNotFoundException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import stateless.CategoryControllerLocal;
import ws.restful.datamodel.CreateCategoryReq;
import ws.restful.datamodel.CreateCategoryRsp;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAllCategoriesRsp;
import ws.restful.datamodel.RetrieveCategoryRsp;

/**
 * REST Web Service
 *
 * @author MX
 */
@Path("Category")
public class CategoryResource {

    @Context
    private UriInfo context;
    
    private CategoryControllerLocal categoryControllerLocal;

    public CategoryResource() {
        categoryControllerLocal = lookupCategoryControllerLocal();
    }

    @Path("retrieveAllCategories")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllCategories() {
        try{
            List <Category> categoryEntities = categoryControllerLocal.retrieveAllCategories();
            for(Category c : categoryEntities){
                c.getRecipes().clear();
                c.getSubscriptionPlans().clear();
            }
            RetrieveAllCategoriesRsp retrieveAllCategoriesRsp = new RetrieveAllCategoriesRsp(categoryEntities);
            return Response.status(Status.OK).entity(retrieveAllCategoriesRsp).build();
        }
        catch(Exception ex){
            ex.printStackTrace();
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    
    @Path("retrieveCategoryById/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCategoryById(@PathParam("id") Long id) {
        try
        {
            Category category = categoryControllerLocal.retrieveCategoryById(id);
            
            category.getSubscriptionPlans().clear();
            category.getRecipes().clear();
            
            RetrieveCategoryRsp retrieveSubscriberRsp = new RetrieveCategoryRsp(category);
            return Response.status(Status.OK).entity(retrieveSubscriberRsp).build();
        }
        catch(CategoryNotFoundException ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(errorRsp).build();
        }
        catch (Exception ex){
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCategory(CreateCategoryReq createCategoryReq) {
        if(createCategoryReq != null){
            try{
                Category category = categoryControllerLocal.createNewCategory(createCategoryReq.getCategory());
                CreateCategoryRsp createCategoryRsp = new CreateCategoryRsp(category);
                return Response.status(Status.OK).entity(createCategoryRsp).build();
            }
            catch(Exception ex){
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create category request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }

    private CategoryControllerLocal lookupCategoryControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (CategoryControllerLocal) c.lookup("java:global/RedApron/RedApron-ejb/CategoryController!stateless.CategoryControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
