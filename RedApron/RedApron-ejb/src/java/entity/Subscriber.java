/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import util.security.CryptographicHelper;

/**
 *
 * @author Bryan
 */
@Entity
public class Subscriber implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriberId;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String email;
    private String addressLine1;
    private String addressLine2;
    private Integer postalCode;
    
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    @NotNull
    private String password;
    
    private String phoneNumber;
    
    @Column(columnDefinition = "CHAR(32) NOT NULL")
    private String salt;
    
    @OneToMany
    private List<SubscriptionPlan> subscriptionPlans;
    
    @OneToMany
    private List<Review> reviews;
    
    @OneToMany
    private List<Enquiry> enquiries;

    public Subscriber() {
        this.salt = CryptographicHelper.getInstance().generateRandomString(32);
    }

    public Subscriber(String firstName, String lastName, String email, String phoneNumber, String addressLine1, String addressLine2, Integer postalCode, String password) {
        this();
        
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.postalCode = postalCode;
        
        setPassword(password);
    }
    

    public Long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Long subscriberId) {
        this.subscriberId = subscriberId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subscriberId != null ? subscriberId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the subscriberId fields are not set
        if (!(object instanceof Subscriber)) {
            return false;
        }
        Subscriber other = (Subscriber) object;
        if ((this.subscriberId == null && other.subscriberId != null) || (this.subscriberId != null && !this.subscriberId.equals(other.subscriberId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Subscriber[ id=" + subscriberId + " ]";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public Integer getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(Integer postalCode) {
        this.postalCode = postalCode;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword2(String password) {
        this.password = password;
    }
    
    public void hashPassword() {
        this.password = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(this.password + this.salt));
    }
    
    public void setPassword(String password) {
        if(password != null)
        {
            this.password = password;
//            this.password = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + this.salt));
        }
        else
        {
            this.password = null;
        }
    }

    public List<SubscriptionPlan> getSubscriptionPlans() {
        return subscriptionPlans;
    }

    public void setSubscriptionPlans(List<SubscriptionPlan> subscriptionPlans) {
        this.subscriptionPlans = subscriptionPlans;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    public void setEnquiries(List<Enquiry> enquiries) {
        this.enquiries = enquiries;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
    
}
