/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Transaction;

/**
 *
 * @author MX
 */
public class UpdateTransactionReq {
    private Transaction transaction;

    public UpdateTransactionReq() {
    }

    public UpdateTransactionReq(Transaction transaction) {
        this.transaction = transaction;
    }

    /**
     * @return the transaction
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * @param transaction the transaction to set
     */
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    
    
}
