/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author MX
 */
@Entity
public class Catergory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catergoryId;
    private String name; 
    private Boolean isAvailable;
    
    @ManyToMany
    private List<Recipe> recipes;
    
    @OneToMany
    private List<SubscriptionPlan> subscriptionPlans;

    public Catergory() {
    }

    public Catergory(String name, Boolean isAvailable) {
        this.name = name;
        this.isAvailable = isAvailable;
    }

    public Long getCatergoryId() {
        return catergoryId;
    }

    public void setCatergoryId(Long catergoryId) {
        this.catergoryId = catergoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (catergoryId != null ? catergoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Catergory)) {
            return false;
        }
        Catergory other = (Catergory) object;
        if ((this.catergoryId == null && other.catergoryId != null) || (this.catergoryId != null && !this.catergoryId.equals(other.catergoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Catergory[ id=" + catergoryId + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public List<SubscriptionPlan> getSubscriptionPlans() {
        return subscriptionPlans;
    }

    public void setSubscriptionPlans(List<SubscriptionPlan> subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
    }
    
}
