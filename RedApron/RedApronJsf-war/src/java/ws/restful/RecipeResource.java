/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import entity.Category;
import entity.Recipe;
import exceptions.RecipeNotFoundException;
import java.util.List;
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
import stateless.CategoryControllerLocal;
import stateless.RecipeControllerLocal;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAllRecipesRsp;
import ws.restful.datamodel.RetrieveRecipeRsp;

/**
 * REST Web Service
 *
 * @author MX
 */
@Path("Recipe")
public class RecipeResource {

    private CategoryControllerLocal categoryControllerLocal;

    private RecipeControllerLocal recipeControllerLocal;

    @Context
    private UriInfo context;

    public RecipeResource() {
        recipeControllerLocal = lookupRecipeControllerLocal();
        categoryControllerLocal = lookupCategoryControllerLocal();
    }

    @Path("retrieveRecipeById/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveRecipeById(@PathParam("id") Long id) {
        try {
            Recipe recipe = recipeControllerLocal.retrieveRecipeById(id);

            //detach the two way bidirectional relationship
            recipe.getCategories().clear();
            recipe.getSteps().clear();
            recipe.getReviews().clear();

            RetrieveRecipeRsp retrieveRecipeRsp = new RetrieveRecipeRsp(recipe);
            return Response.status(Response.Status.OK).entity(retrieveRecipeRsp).build();
        } catch (RecipeNotFoundException ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        } catch (Exception ex) {
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveAllRecipes")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllRecipes() {
        try {
            List<Recipe> recipeEntities = recipeControllerLocal.retrieveAllRecipes();

            for (Recipe r : recipeEntities) {
                r.getReviews().clear();
                r.getCategories().clear();
                r.getSteps().clear();
            }

            RetrieveAllRecipesRsp retrieveAllRecipesRsp = new RetrieveAllRecipesRsp(recipeEntities);
            return Response.status(Status.OK).entity(retrieveAllRecipesRsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }

    @Path("retrieveRecipesByCategoryId/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveRecipesByCategoryId(@PathParam("id") Long id) {
        try {
            List<Recipe> recipes = categoryControllerLocal.retrieveRecipesByCategoryId(id);
            System.out.println(recipes);
            for (Recipe r : recipes) {
               r.getCategories().clear();
                r.getReviews().clear();
                r.getSteps().clear();
 
            }
            RetrieveAllRecipesRsp retrieveAllRecipesRsp = new RetrieveAllRecipesRsp(recipes);
            return Response.status(Status.OK).entity(retrieveAllRecipesRsp).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
        }
    }
    


    private RecipeControllerLocal lookupRecipeControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (RecipeControllerLocal) c.lookup("java:global/RedApron/RedApron-ejb/RecipeController!stateless.RecipeControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
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
