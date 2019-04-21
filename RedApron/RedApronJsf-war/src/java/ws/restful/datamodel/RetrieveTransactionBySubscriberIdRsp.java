/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws.restful.datamodel;

import entity.Transaction;
import java.util.List;

/**
 *
 * @author Bryan
 */
public class RetrieveTransactionBySubscriberIdRsp {

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    private List<Transaction> transactions;

    public RetrieveTransactionBySubscriberIdRsp() {
    }

    public RetrieveTransactionBySubscriberIdRsp(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    
    
}
