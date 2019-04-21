/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.Transaction;
import exceptions.TransactionNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author matthealoo
 */
public interface TransactionControllerLocal {

    public Transaction createNewTransaction(Transaction transaction);

    public Transaction retrieveAnswerById(Long transactionId) throws TransactionNotFoundException;

    public List<Transaction> retrieveAllTransactions();

    public List<Transaction> retrieveThisMonthTransaction();

    public List<Transaction> retrieveTransactionWithSubscriberId(Long subscriberId);

    public List<BigDecimal> retrieveSixMonthsTransactions();


}
