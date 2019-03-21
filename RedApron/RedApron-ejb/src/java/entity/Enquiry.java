/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 *
 * @author matthealoo
 */
@Entity
public class Enquiry implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enquiryId;
    private String text;
    private Date created;
    private Date updated;
    
    
    @ManyToOne
    private Subscriber subscriber;
    
    @OneToOne
    private Answer answer;
    
    
    public Enquiry() {
    }

    public Enquiry(String text, Subscriber subscriber) {
        this.text = text;
        this.subscriber = subscriber;
    }
    
    @PrePersist
    protected void onCreate(){
        this.created = new Date();
    }
    
    @PreUpdate
    protected void onUpdate(){
        this.updated = new Date();
    }
    
    
    
    
    
    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
    

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Subscriber getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(Subscriber subscriber) {
        this.subscriber = subscriber;
    }
    
    public Long getEnquiryId() {
        return enquiryId;
    }

    public void setEnquiryId(Long enquiryId) {
        this.enquiryId = enquiryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (enquiryId != null ? enquiryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the enquiryId fields are not set
        if (!(object instanceof Enquiry)) {
            return false;
        }
        Enquiry other = (Enquiry) object;
        if ((this.enquiryId == null && other.enquiryId != null) || (this.enquiryId != null && !this.enquiryId.equals(other.enquiryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Enquiry[ id=" + enquiryId + " ]";
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    
}
