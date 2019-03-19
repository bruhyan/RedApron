/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Subscriber;
import entity.SubscriptionPlan;
import entity.Transaction;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import stateless.SubscriptionPlanControllerLocal;
import stateless.TransactionControllerLocal;

/**
 *
 * @author matthealoo
 */
@Named(value = "transactionManagedBean")
@RequestScoped
public class TransactionManagedBean {

    @EJB
    private SubscriptionPlanControllerLocal subscriptionPlanController;
    @EJB
    private TransactionControllerLocal transactionController;

    private List<Transaction> transactionEntities;
    private List<Transaction> filteredTransactionEntities;

    
    /**
     * Creates a new instance of TransactionManagedBean
     */
    public TransactionManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct()
    {
        transactionEntities = transactionController.retrieveAllTransactions();
    }

    public List<Transaction> getTransactionEntities() {
        return transactionEntities;
    }

    public void setTransactionEntities(List<Transaction> transactionEntities) {
        this.transactionEntities = transactionEntities;
    }

    public List<Transaction> getFilteredTransactionEntities() {
        return filteredTransactionEntities;
    }

    public void setFilteredTransactionEntities(List<Transaction> filteredTransactionEntities) {
        this.filteredTransactionEntities = filteredTransactionEntities;
    }
    
    
    
}
