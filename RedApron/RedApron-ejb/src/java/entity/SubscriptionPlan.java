/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enumeration.DeliveryDay;
import enumeration.SubscriptionPlanStatus;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Bryan
 */
@Entity
public class SubscriptionPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionPlanId;
    private Date startDate;
    private Date endDate;
    private String preferences;
    private Integer numOfWeeks;
    private Integer numOfRecipes;
    private SubscriptionPlanStatus status;
    private DeliveryDay deliveryDay;
    
    @ManyToOne
    private Category catergory;
    
    @OneToOne
    private Transaction transaction;
    
    @OneToMany
    private List<Recipe> recipes;
    
    @ManyToOne
    private Subscriber subscriber;

    public SubscriptionPlan() {
    }

    public SubscriptionPlan(Date startDate, Date endDate, String preferences, Integer numOfWeeks, Integer numOfRecipes, SubscriptionPlanStatus status, DeliveryDay deliveryDay) {
        
        this.startDate = startDate;
        this.endDate = endDate;
        this.preferences = preferences;
        this.numOfWeeks = numOfWeeks;
        this.numOfRecipes = numOfRecipes;
        this.status = status;
        this.deliveryDay = deliveryDay;
    }
    

    public Long getSubscriptionPlanId() {
        return subscriptionPlanId;
    }

    public void setSubscriptionPlanId(Long subscriptionPlanId) {
        this.subscriptionPlanId = subscriptionPlanId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subscriptionPlanId != null ? subscriptionPlanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the subscriptionPlanId fields are not set
        if (!(object instanceof SubscriptionPlan)) {
            return false;
        }
        SubscriptionPlan other = (SubscriptionPlan) object;
        if ((this.subscriptionPlanId == null && other.subscriptionPlanId != null) || (this.subscriptionPlanId != null && !this.subscriptionPlanId.equals(other.subscriptionPlanId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SubscriptionPlan[ id=" + subscriptionPlanId + " ]";
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

    public Integer getNumOfWeeks() {
        return numOfWeeks;
    }

    public void setNumOfWeeks(Integer numOfWeeks) {
        this.numOfWeeks = numOfWeeks;
    }

    public Integer getNumOfRecipes() {
        return numOfRecipes;
    }

    public void setNumOfRecipes(Integer numOfRecipes) {
        this.numOfRecipes = numOfRecipes;
    }

    public SubscriptionPlanStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriptionPlanStatus status) {
        this.status = status;
    }

    public DeliveryDay getDeliveryDay() {
        return deliveryDay;
    }

    public void setDeliveryDay(DeliveryDay deliveryDay) {
        this.deliveryDay = deliveryDay;
    }

    /**
     * @return the catergory
     */
    public Category getCatergory() {
        return catergory;
    }

    public void setCatergory(Category catergory) {
        this.catergory = catergory;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }
    
}
