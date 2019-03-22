/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedBean;

import entity.Answer;
import entity.Enquiry;
import entity.Subscriber;
import exceptions.AnswerNotFoundException;
import exceptions.EnquiryNotFoundException;
import exceptions.SubscriberNotFoundException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import stateless.AnswerControllerLocal;
import stateless.EnquiryControllerLocal;
import stateless.SubscriberControllerLocal;

/**
 *
 * @author mdk12
 */
@Named(value = "enquiryManagementManagedBean")
@ViewScoped
public class EnquiryManagementManagedBean implements Serializable {

    @EJB(name = "AnswerControllerLocal")
    private AnswerControllerLocal answerControllerLocal;

    @EJB(name = "SubscriberControllerLocal")
    private SubscriberControllerLocal subscriberControllerLocal;

    @EJB(name = "EnquiryControllerLocal")
    private EnquiryControllerLocal enquiryControllerLocal;

    /**
     * Creates a new instance of EnquiryManagementManagedBean
     */
    private List<Enquiry> enquiries;
    private List<Enquiry> filteredEnquiriesEntities;
    private Enquiry selectedEnquiryToView;
    private Enquiry newEnquiry;
    private Enquiry enquiryToUpdate;
    private Enquiry selectedEnquiryToReply;
    private Enquiry enquiryToDelete;
    private Answer answer;
    private Long askingSubscriberId;

    public EnquiryManagementManagedBean() {
        this.newEnquiry = new Enquiry();
        this.answer = new Answer();
    }

    @PostConstruct
    public void postConstruct() {
        this.setEnquiries(enquiryControllerLocal.retrieveAllEnquiries());
    }

    public void createNewEnquiry(ActionEvent event) {

        try {
            Enquiry enquiry = enquiryControllerLocal.createNewEnquiry(getNewEnquiry());
            Subscriber subscriber = (Subscriber) event.getComponent().getAttributes().get("askingSubscriberId");
            Subscriber asker = subscriberControllerLocal.retrieveSubscriberById(subscriber.getSubscriberId());
            enquiry.setSubscriber(asker);
            getEnquiries().add(enquiry);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New Enquiry created successfully (Enquiry ID: " + enquiry.getEnquiryId() + ", Subscriber: " + enquiry.getSubscriber().getEmail() + " )", null));

        } catch (SubscriberNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void deleteEnquiry(ActionEvent event) {

        try {
            Enquiry enquiryToDelete = (Enquiry) event.getComponent().getAttributes().get("enquiryToDelete");
            enquiryControllerLocal.deleteEnquiry(enquiryToDelete.getEnquiryId());

            getEnquiries().remove(enquiryToDelete);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enquiry deleted successfully", null));
        } catch (EnquiryNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public void doUpdateEnquiry(ActionEvent event) {
        setEnquiryToUpdate((Enquiry) event.getComponent().getAttributes().get("enquiryToUpdate"));
        Answer answer = answerControllerLocal.createNewAnswer(getAnswer());

        enquiryToUpdate.setAnswer(answer);
    }

    public void updateEnquiry(ActionEvent event) {

        try {
            answerControllerLocal.updateAnswer(getEnquiryToUpdate().getAnswer());
            System.out.println(getEnquiryToUpdate().getAnswer().getText());
            enquiryControllerLocal.updateEnquiry(getEnquiryToUpdate());

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enquiry updated successfully", null));
        } catch (EnquiryNotFoundException | AnswerNotFoundException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }

    }

    /**
     * @return the enquiries
     */
    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    /**
     * @param enquiries the enquiries to set
     */
    public void setEnquiries(List<Enquiry> enquiries) {
        this.enquiries = enquiries;
    }

    /**
     * @return the filteredEnquiriesEntities
     */
    public List<Enquiry> getFilteredEnquiriesEntities() {
        return filteredEnquiriesEntities;
    }

    /**
     * @param filteredEnquiriesEntities the filteredEnquiriesEntities to set
     */
    public void setFilteredEnquiriesEntities(List<Enquiry> filteredEnquiriesEntities) {
        this.filteredEnquiriesEntities = filteredEnquiriesEntities;
    }

    /**
     * @return the selectedEnquiryToView
     */
    public Enquiry getSelectedEnquiryToView() {
    
            
           
            return selectedEnquiryToView;
    
       
    }

    /**
     * @param selectedEnquiryToView the selectedEnquiryToView to set
     */
    public void setSelectedEnquiryToView(Enquiry selectedEnquiryToView) {
        this.selectedEnquiryToView = selectedEnquiryToView;
    }

    /**
     * @return the newEnquiry
     */
    public Enquiry getNewEnquiry() {
        return newEnquiry;
    }

    /**
     * @param newEnquiry the newEnquiry to set
     */
    public void setNewEnquiry(Enquiry newEnquiry) {
        this.newEnquiry = newEnquiry;
    }

    /**
     * @return the enquiryToUpdate
     */
    public Enquiry getEnquiryToUpdate() {
        return enquiryToUpdate;
    }

    /**
     * @param enquiryToUpdate the enquiryToUpdate to set
     */
    public void setEnquiryToUpdate(Enquiry enquiryToUpdate) {
        this.enquiryToUpdate = enquiryToUpdate;
    }

    /**
     * @return the selectedEnquiryToReply
     */
    public Enquiry getSelectedEnquiryToReply() {
        return selectedEnquiryToReply;
    }

    /**
     * @param selectedEnquiryToReply the selectedEnquiryToReply to set
     */
    public void setSelectedEnquiryToReply(Enquiry selectedEnquiryToReply) {
        this.selectedEnquiryToReply = selectedEnquiryToReply;
    }

    /**
     * @return the enquiryToDelete
     */
    public Enquiry getEnquiryToDelete() {
        return enquiryToDelete;
    }

    /**
     * @param enquiryToDelete the enquiryToDelete to set
     */
    public void setEnquiryToDelete(Enquiry enquiryToDelete) {
        this.enquiryToDelete = enquiryToDelete;
    }

    /**
     * @return the askingSubscriberId
     */
    public Long getAskingSubscriberId() {
        return askingSubscriberId;
    }

    /**
     * @param askingSubscriberId the askingSubscriberId to set
     */
    public void setAskingSubscriberId(Long askingSubscriberId) {
        this.askingSubscriberId = askingSubscriberId;
    }

    /**
     * @return the answer
     */
    public Answer getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

}
