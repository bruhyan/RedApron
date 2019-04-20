/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import stateless.TransactionControllerLocal;

/**
 *
 * @author mdk12
 */
@Named(value = "analyticsMonthlyRevenueManagedBean")
@RequestScoped
public class AnalyticsMonthlyRevenueManagedBean {

    @EJB(name = "TransactionControllerLocal")
    private TransactionControllerLocal transactionControllerLocal;

    private LineChartModel areaModel;

    /**
     * Creates a new instance of AnalyticsMonthlyRevenueManagedBean
     */

    @PostConstruct
    public void init() {
        createAreaModel();
    }

    public AnalyticsMonthlyRevenueManagedBean() {
    }

    public CartesianChartModel getAreaModel() {
        return areaModel;
    }

    private void createAreaModel() {
        areaModel = new LineChartModel();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        Date currDate = new Date();
        String dateTodayStr = dateFormat.format(currDate);
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.MONTH, -6);
        Date date6monthsAgo = cal.getTime();
        String date6monthsAgoStr = dateFormat.format(date6monthsAgo); 
        cal.add(Calendar.MONTH, 1);
        Date date5monthsAgo = cal.getTime();
        String date5monthsAgoStr = dateFormat.format(date5monthsAgo); 
        cal.add(Calendar.MONTH, 1);
        Date date4monthsAgo = cal.getTime();
        String date4monthsAgoStr = dateFormat.format(date4monthsAgo); 
        cal.add(Calendar.MONTH, 1);
        Date date3monthsAgo = cal.getTime();
        String date3monthsAgoStr = dateFormat.format(date3monthsAgo); 
        cal.add(Calendar.MONTH, 1);
        Date date2monthsAgo = cal.getTime();
        String date2monthsAgoStr = dateFormat.format(date2monthsAgo); 
        cal.add(Calendar.MONTH, 1);
        Date date1monthsAgo = cal.getTime();
        String date1monthsAgoStr = dateFormat.format(date1monthsAgo); 
        
        
        
        List<BigDecimal> transactions = transactionControllerLocal.retrieveSixMonthsTransactions();
        
        
        LineChartSeries revenue = new LineChartSeries();
        revenue.setFill(true);
        revenue.setLabel("Revenue");
        revenue.set(date6monthsAgoStr, transactions.get(0).doubleValue());
        revenue.set(date5monthsAgoStr, transactions.get(1).doubleValue());
        revenue.set(date4monthsAgoStr, transactions.get(2).doubleValue());
        revenue.set(date3monthsAgoStr, transactions.get(3).doubleValue());
        revenue.set(date2monthsAgoStr, transactions.get(4).doubleValue());
        revenue.set(date1monthsAgoStr, transactions.get(5).doubleValue());
        BigDecimal largest = BigDecimal.ZERO;
        for(BigDecimal bd : transactions){
            if(bd.compareTo(largest) == 1){
                largest = bd;
            }
        }

        areaModel.addSeries(revenue);

        areaModel.setTitle("Monthly Revenue From Transactions");
        areaModel.setLegendPosition("nw");
        areaModel.setStacked(true);
        areaModel.setShowPointLabels(true);
        areaModel.setAnimate(true);

        Axis xAxis = new CategoryAxis("Months");
        areaModel.getAxes().put(AxisType.X, xAxis);
        Axis yAxis = areaModel.getAxis(AxisType.Y);
        yAxis.setLabel("SGD S$)");
        yAxis.setMin(0);
        yAxis.setMax(largest);
    }

}
