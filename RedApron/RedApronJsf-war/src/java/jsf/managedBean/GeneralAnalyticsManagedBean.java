/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Subscriber;
import entity.Transaction;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import stateless.SubscriberControllerLocal;
import stateless.TransactionControllerLocal;

/**
 *
 * @author mdk12
 */
@Named(value = "generalAnalyticsManagedBean")
@ViewScoped
public class GeneralAnalyticsManagedBean implements Serializable {

    @EJB(name = "TransactionControllerLocal")
    private TransactionControllerLocal transactionControllerLocal;

    @EJB(name = "SubscriberControllerLocal")
    private SubscriberControllerLocal subscriberControllerLocal;
    private int totalNumberOfSubscribers;
    private String thisMonth;
    private int thisMonthTransactionsNum;
    private float thisMonthProfit;
    private List<Transaction> thisMonthTransactions;

    public GeneralAnalyticsManagedBean() {

    }

    @PostConstruct
    public void init() {
        Format formatter = new SimpleDateFormat("MMMM");
        this.thisMonth = formatter.format(new Date());

        List<Subscriber> subscribers = subscriberControllerLocal.retrieveAllSubscribers();
        this.totalNumberOfSubscribers = subscribers.size();
        this.thisMonthTransactions = transactionControllerLocal.retrieveThisMonthTransaction();
        this.thisMonthTransactionsNum = thisMonthTransactions.size();
        float sum = 0;
        for (Transaction thisMonthTransaction : thisMonthTransactions) {
            sum += thisMonthTransaction.getAmount();
        }
        this.thisMonthProfit = sum;

    }

    public int getTotalNumberOfSubscribers() {
        return totalNumberOfSubscribers;
    }

    public void setTotalNumberOfSubscribers(int totalNumberOfSubscribers) {
        this.totalNumberOfSubscribers = totalNumberOfSubscribers;
    }

    public String getThisMonth() {
        return thisMonth;
    }

    public void setThisMonth(String thisMonth) {
        this.thisMonth = thisMonth;
    }

    public int getThisMonthTransactionsNum() {
        return thisMonthTransactionsNum;
    }

    public void setThisMonthTransactionsNum(int thisMonthTransactionsNum) {
        this.thisMonthTransactionsNum = thisMonthTransactionsNum;
    }

    public float getThisMonthProfit() {
        return thisMonthProfit;
    }

    public void setThisMonthProfit(float thisMonthProfit) {
        this.thisMonthProfit = thisMonthProfit;
    }

    public List<Transaction> getThisMonthTransactions() {
        return thisMonthTransactions;
    }

    public void setThisMonthTransactions(List<Transaction> thisMonthTransactions) {
        this.thisMonthTransactions = thisMonthTransactions;
    }

    
}
