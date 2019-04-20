/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.SubscriptionPlan;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import stateless.SubscriptionPlanControllerLocal;

/**
 *
 * @author MX
 */
@Named(value = "analyticNumOfSubscriberManagedBean")
@ViewScoped
public class AnalyticNumOfSubscriberManagedBean implements Serializable {

    @EJB
    private SubscriptionPlanControllerLocal subscriptionPlanControllerLocal;

    private LineChartModel lineModel;

    public AnalyticNumOfSubscriberManagedBean() {
    }

    @PostConstruct
    public void init() {
        lineModel = new LineChartModel();
        lineModel.setAnimate(true);
        LineChartSeries s = new LineChartSeries();
        lineModel.setTitle("Total Number of Subscribers (Cumulative)");

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

        int largest = 0;
        int size = 0;
        List<SubscriptionPlan> numOfsubscriptionPlan6monthsAgo = subscriptionPlanControllerLocal.retrieveSubscriptionPlanByDate(date6monthsAgo);
        size = numOfsubscriptionPlan6monthsAgo.size();
        if (largest < size) {
            largest = size;
        }
        s.set(date6monthsAgoStr, numOfsubscriptionPlan6monthsAgo.size());

        List<SubscriptionPlan> numOfsubscriptionPlan5monthsAgo = subscriptionPlanControllerLocal.retrieveSubscriptionPlanByDate(date5monthsAgo);
        size = numOfsubscriptionPlan5monthsAgo.size();
        if (largest < size) {
            largest = size;
        }
        s.set(date5monthsAgoStr, numOfsubscriptionPlan5monthsAgo.size());

        List<SubscriptionPlan> numOfsubscriptionPlan4monthsAgo = subscriptionPlanControllerLocal.retrieveSubscriptionPlanByDate(date4monthsAgo);
        size = numOfsubscriptionPlan4monthsAgo.size();
        if (largest < size) {
            largest = size;
        }
        s.set(date4monthsAgoStr, numOfsubscriptionPlan4monthsAgo.size());

        List<SubscriptionPlan> numOfsubscriptionPlan3monthsAgo = subscriptionPlanControllerLocal.retrieveSubscriptionPlanByDate(date3monthsAgo);
        size = numOfsubscriptionPlan3monthsAgo.size();
        if (largest < size) {
            largest = size;
        }
        s.set(date3monthsAgoStr, numOfsubscriptionPlan3monthsAgo.size());

        List<SubscriptionPlan> numOfsubscriptionPlan2monthsAgo = subscriptionPlanControllerLocal.retrieveSubscriptionPlanByDate(date2monthsAgo);
        size = numOfsubscriptionPlan2monthsAgo.size();
        if (largest < size) {
            largest = size;
        }
        s.set(date2monthsAgoStr, numOfsubscriptionPlan2monthsAgo.size());

        List<SubscriptionPlan> numOfsubscriptionPlan1monthsAgo = subscriptionPlanControllerLocal.retrieveSubscriptionPlanByDate(date1monthsAgo);
        size = numOfsubscriptionPlan1monthsAgo.size();
        if (largest < size) {
            largest = size;
        }
        s.set(date1monthsAgoStr, numOfsubscriptionPlan1monthsAgo.size());

        List<SubscriptionPlan> numOfsubscriptionPlanToday = subscriptionPlanControllerLocal.retrieveSubscriptionPlanByDate(currDate);
        size = numOfsubscriptionPlanToday.size();
        if (largest < size) {
            largest = size;
        }
        s.set(dateTodayStr, numOfsubscriptionPlanToday.size());

        lineModel.addSeries(s);
        Axis y = lineModel.getAxis(AxisType.Y);
        y.setMin(0);
        y.setMax(largest);
        y.setLabel("Subscriber");

        DateAxis x = new DateAxis("Dates");
        x.setMax(dateFormat.format(currDate));
        x.setTickFormat("%b %#d, %y");;
        x.setLabel("Monthly Count");
        lineModel.getAxes().put(AxisType.X, x);
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

}
