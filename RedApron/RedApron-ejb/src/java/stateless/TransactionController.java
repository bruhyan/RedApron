/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Transaction;
import exceptions.TransactionNotFoundException;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author matthealoo
 */
@Stateless
@Local(TransactionControllerLocal.class)
public class TransactionController implements TransactionControllerLocal {

    @PersistenceContext(unitName = "RedApron-ejbPU")
    private EntityManager em;

    @Override
    public Transaction createNewTransaction(Transaction transaction) {
        em.persist(transaction);
        em.flush();
        
        return transaction;
    }
    
    @Override
    public Transaction retrieveAnswerById(Long transactionId) throws TransactionNotFoundException {
        Transaction transaction = em.find(Transaction.class, transactionId);

        if (transaction != null) {
            return transaction;
        } else {
            throw new TransactionNotFoundException("Answer ID " + transactionId + " does not exist");
        }
    }
    
    @Override
    public List<Transaction> retrieveAllTransactions() {
       Query query = em.createQuery("SELECT t FROM Transaction t ORDER BY t.transactionId ASC");
        List<Transaction> transactionEntities = query.getResultList();
        
        for(Transaction transactionEntity:transactionEntities)
        {
            transactionEntity.getSubscriptionPlan();
        }
        
        return transactionEntities;
    }
    
}

