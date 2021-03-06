/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stateless;

import entity.SubscriptionPlan;
import entity.Transaction;
import exceptions.SubscriptionPlanNotFoundException;
import exceptions.TransactionNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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

    @EJB(name = "SubscriptionPlanControllerLocal")
    private SubscriptionPlanControllerLocal subscriptionPlanControllerLocal;

    @PersistenceContext(unitName = "RedApron-ejbPU")
    private EntityManager em;

    @Override
    public Transaction createNewTransaction(Transaction transaction) {
        em.persist(transaction);
        if (transaction.getSubscriptionPlan() != null) {
            try {

                SubscriptionPlan subPlan = subscriptionPlanControllerLocal.retrieveSubscriptionPlanById(transaction.getSubscriptionPlan().getSubscriptionPlanId());
                subPlan.setTransaction(transaction);
            } catch (SubscriptionPlanNotFoundException ex) {
                Logger.getLogger(SubscriptionPlanController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
    public List<Transaction> retrieveThisMonthTransaction() {

        Calendar dCal = Calendar.getInstance();
        dCal.set(Calendar.DAY_OF_MONTH, 1);
        dCal.set(Calendar.HOUR_OF_DAY, 0);
        dCal.set(Calendar.MINUTE, 0);
        dCal.set(Calendar.SECOND, 0);
        dCal.set(Calendar.MILLISECOND, 0);

        Query query = em.createQuery("SELECT t FROM Transaction t WHERE t.paymentDate BETWEEN :start AND :end");
//        System.out.println(dCal.getTime());
        query.setParameter("start", dCal.getTime());

        int endDate = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
        dCal.set(Calendar.DAY_OF_MONTH, endDate);
//        System.out.println(dCal.getTime());
        query.setParameter("end", dCal.getTime());
        return query.getResultList();
    }

    @Override
    public List<BigDecimal> retrieveSixMonthsTransactions() {

        Calendar dCal = Calendar.getInstance();
        dCal.set(Calendar.DAY_OF_MONTH, 1);
        dCal.set(Calendar.HOUR_OF_DAY, 0);
        dCal.set(Calendar.MINUTE, 0);
        dCal.set(Calendar.SECOND, 0);
        dCal.set(Calendar.MILLISECOND, 0);
        dCal.add(Calendar.MONTH, -6);

        List<BigDecimal> monthlyTransactions = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            Query query = em.createQuery("SELECT t.amount FROM Transaction t WHERE t.paymentDate BETWEEN :start AND :end");
            System.out.println(dCal.getTime());
            query.setParameter("start", dCal.getTime());
            int endDate = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);
            dCal.set(Calendar.DAY_OF_MONTH, endDate);
            query.setParameter("end", dCal.getTime());
            System.out.println(dCal.getTime());
            List<Double> monthAmount = query.getResultList();
            Double monthSum = 0.0;
            for (Double amount : monthAmount) {
                monthSum += amount;
            }
            System.out.println(monthAmount);
            monthlyTransactions.add(BigDecimal.valueOf(monthSum));
            dCal.add(Calendar.MONTH, 1);
        }
        System.out.println(monthlyTransactions);
        return monthlyTransactions;
    }

    @Override
    public List<Transaction> retrieveAllTransactions() {
        Query query = em.createQuery("SELECT t FROM Transaction t ORDER BY t.transactionId ASC");
        List<Transaction> transactionEntities = query.getResultList();

        for (Transaction transactionEntity : transactionEntities) {
            transactionEntity.getSubscriptionPlan();
        }

        return transactionEntities;
    }

    @Override
    public List<Transaction> retrieveTransactionWithSubscriberId(Long subscriberId) {
        Query query = em.createQuery("SELECT t FROM Transaction t WHERE t.subscriptionPlan.subscriber.subscriberId = :inSubscriberId");
        query.setParameter("inSubscriberId", subscriberId);
        List<Transaction> transactionEntities = query.getResultList();
        for (Transaction transactionEntity : transactionEntities) {
            transactionEntity.getSubscriptionPlan();
        }

        return transactionEntities;
    }

}
