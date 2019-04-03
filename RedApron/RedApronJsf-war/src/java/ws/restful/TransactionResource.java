/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful;

import entity.Transaction;
import exceptions.TransactionNotFoundException;
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
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import stateless.TransactionControllerLocal;
import ws.restful.datamodel.CreateTransactionReq;
import ws.restful.datamodel.CreateTransactionRsp;
import ws.restful.datamodel.ErrorRsp;
import ws.restful.datamodel.RetrieveAllTransactionsRsp;
import ws.restful.datamodel.UpdateTransactionReq;

/**
 * REST Web Service
 *
 * @author MX
 */
@Path("Transaction")
public class TransactionResource {

    private TransactionControllerLocal transactionControllerLocal;

    @Context
    private UriInfo context;

    public TransactionResource() {
        transactionControllerLocal = lookupTransactionControllerLocal();
    }
    
    @Path("retrieveAllTransactions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllTransactions(){
        try{
            List <Transaction> transactionEntities = transactionControllerLocal.retrieveAllTransactions();
            
            for(Transaction transaction:transactionEntities)
            {
                transaction.setSubscriptionPlan(null);
            }
            
            RetrieveAllTransactionsRsp retrieveAllSubscribersRsp = new RetrieveAllTransactionsRsp(transactionEntities);
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
    public Response createTransaction(CreateTransactionReq createTransactionReq){
        if(createTransactionReq != null){
            try{
                Transaction transaction = transactionControllerLocal.createNewTransaction(createTransactionReq.getTransaction());
                CreateTransactionRsp createTransactionRsp = new CreateTransactionRsp(transaction);
                return Response.status(Status.OK).entity(createTransactionRsp).build();
            }
            catch(Exception ex){
                ErrorRsp errorRsp = new ErrorRsp(ex.getMessage());
                return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorRsp).build();
            }
        }else {
            ErrorRsp errorRsp = new ErrorRsp("Invalid create transaction request");
            
            return Response.status(Response.Status.BAD_REQUEST).entity(errorRsp).build();
        }
    }
    
    
    private TransactionControllerLocal lookupTransactionControllerLocal() {
        try {
            javax.naming.Context c = new InitialContext();
            return (TransactionControllerLocal) c.lookup("java:global/RedApron/RedApron-ejb/TransactionController!stateless.TransactionControllerLocal");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
}
