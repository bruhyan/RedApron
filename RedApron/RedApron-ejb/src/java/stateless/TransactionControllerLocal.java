/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Transaction;
import exceptions.TransactionNotFoundException;
import javax.ejb.Local;

/**
 *
 * @author matthealoo
 */
public interface TransactionControllerLocal {
    
     public Transaction createNewTransaction(Transaction transaction);
    public Transaction retrieveAnswerById(Long transactionId) throws TransactionNotFoundException;

    
}
